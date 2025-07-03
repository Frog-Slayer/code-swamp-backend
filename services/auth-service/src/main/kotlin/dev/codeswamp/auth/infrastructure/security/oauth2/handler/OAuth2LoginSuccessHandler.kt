package dev.codeswamp.auth.infrastructure.security.oauth2.handler

import dev.codeswamp.auth.application.dto.ValidatedTokenPair
import dev.codeswamp.auth.application.port.outgoing.UserProfileFetcher
import dev.codeswamp.auth.application.service.AuthApplicationService
import dev.codeswamp.auth.application.signup.TemporaryTokenService
import dev.codeswamp.auth.domain.model.AuthUser
import dev.codeswamp.auth.infrastructure.security.oauth2.dto.ProviderUserInfo
import dev.codeswamp.auth.infrastructure.security.oauth2.factory.UserInfoFactory
import dev.codeswamp.auth.infrastructure.web.HttpTokenAccessor
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.net.URI

@Component
class OAuth2LoginSuccessHandler(
    @Value("\${frontend.url}") private val frontendUrl: String,
    private val userProfileFetcher: UserProfileFetcher,
    private val userInfoFactory: UserInfoFactory,
    private val authApplicationService: AuthApplicationService,
    private val temporaryTokenService: TemporaryTokenService,
    private val authorizedClientService: ReactiveOAuth2AuthorizedClientService,
    private val tokenAccessor: HttpTokenAccessor
) : ServerAuthenticationSuccessHandler {

    val frontendCallbackUrl: String = "$frontendUrl/auth/callback"

    override fun onAuthenticationSuccess(
        exchange: WebFilterExchange,
        authentication: Authentication?
    ): Mono<Void> {
        return mono {
            val response = exchange.exchange.response
            val oauth2Token = authentication as OAuth2AuthenticationToken
            val oAuth2User = oauth2Token.principal as OAuth2User
            val registrationId = oauth2Token.authorizedClientRegistrationId
            val client = authorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
                registrationId,
                oauth2Token.name
            ).awaitFirstOrNull() ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)

            val oauth2AccessToken = client.accessToken.tokenValue

            val providerUserInfo = userInfoFactory.extract(registrationId, oAuth2User, oauth2AccessToken)
            val authUser = authApplicationService.findByUsername(providerUserInfo.email)

            if (authUser == null) handleNewUser(response, providerUserInfo)
            else handleRegisteredUserAndPassTokens(response, authUser)

            null
        }
    }

    private suspend fun handleNewUser(response: ServerHttpResponse, userInfo: ProviderUserInfo) {
        val signupToken = temporaryTokenService.generateSignupToken(userInfo.email)

        val redirectUrl = UriComponentsBuilder
            .fromUriString(frontendCallbackUrl)
            .queryParam("isNewUser", true)
            .queryParam("signupToken", signupToken)
            .queryParam("email", userInfo.email)
            .queryParam("name", userInfo.name)
            .queryParam("profileImage", userInfo.profileImage)
            .build().encode().toUriString()

        response.statusCode = HttpStatus.FOUND
        response.headers.location = URI.create(redirectUrl)
        return;
    }

    private suspend fun handleRegisteredUserAndPassTokens(response: ServerHttpResponse, user: AuthUser) {
        val accessToken = authApplicationService.issueAccessToken(user)
        val refreshToken = authApplicationService.issueAndStoreRefreshToken(user)

        tokenAccessor.injectTokenPair(response, ValidatedTokenPair(accessToken, refreshToken))

        if (user.id == null) throw Exception(" No User ID") // TODO -> 세분화
        val userProfile = userProfileFetcher.fetchUserProfile(user.id)

        val redirectUrl = UriComponentsBuilder
            .fromUriString(frontendCallbackUrl)
            .queryParam("isNewUser", false)
            .queryParam("accessToken", accessToken.value)
            .queryParam("email", user.email)
            .queryParam("name", userProfile.nickname)//여기
            .queryParam("profileImage", userProfile.profileImage)//여기
            .build().encode().toUriString()

        response.statusCode = HttpStatus.FOUND
        response.headers.location = URI.create(redirectUrl)
    }

}