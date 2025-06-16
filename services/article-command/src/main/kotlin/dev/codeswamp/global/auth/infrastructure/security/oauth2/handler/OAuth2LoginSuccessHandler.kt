package dev.codeswamp.global.auth.infrastructure.security.oauth2.handler

import dev.codeswamp.global.auth.application.acl.UserProfileFetcher
import dev.codeswamp.global.auth.application.dto.ValidatedTokenPair
import dev.codeswamp.global.auth.application.service.AuthApplicationService
import dev.codeswamp.global.auth.application.signup.TemporaryTokenService
import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.infrastructure.security.oauth2.dto.ProviderUserInfo
import dev.codeswamp.global.auth.infrastructure.security.oauth2.factory.UserInfoFactory
import dev.codeswamp.global.auth.infrastructure.web.HttpTokenAccessor
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class OAuth2LoginSuccessHandler(
    private val userProfileFetcher: UserProfileFetcher,
    private val userInfoFactory: UserInfoFactory,
    private val authApplicationService: AuthApplicationService,
    private val temporaryTokenService: TemporaryTokenService,
    private val authorizedClientService: OAuth2AuthorizedClientService,
    private val tokenAccessor: HttpTokenAccessor,
    @Value("\${frontend.url}") private val frontendUrl: String
) : AuthenticationSuccessHandler {

    val frontendCallbackUrl: String = "$frontendUrl/auth/callback"

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val oauth2Token = authentication as OAuth2AuthenticationToken
        val oAuth2User = oauth2Token.principal as OAuth2User
        val registrationId = oauth2Token.authorizedClientRegistrationId
        val oauth2AccessToken = authorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
            registrationId,
            oauth2Token.name
        ).accessToken.tokenValue

        val providerUserInfo = userInfoFactory.extract(registrationId, oAuth2User, oauth2AccessToken)
        val authUser = authApplicationService.findByUsername(providerUserInfo.email)

        if  (authUser == null) {
            handleNewUser(response, providerUserInfo)
            return;
        }

        handleRegisteredUserAndPassTokens(response, authUser)
    }

    private fun handleNewUser(response: HttpServletResponse, userInfo: ProviderUserInfo) {
        val signupToken = temporaryTokenService.generateSignupToken(userInfo.email)

        val redirectUrl = UriComponentsBuilder
            .fromUriString(frontendCallbackUrl)
            .queryParam("isNewUser", true)
            .queryParam("signupToken", signupToken)
            .queryParam("email", userInfo.email)
            .queryParam("name", userInfo.name)
            .queryParam("profileImage",userInfo.profileImage)
            .build().encode().toUriString()

        response.sendRedirect(redirectUrl)
        return;
    }

    private fun handleRegisteredUserAndPassTokens(response: HttpServletResponse, user: AuthUser) {
        val accessToken = authApplicationService.issueAccessToken(user)
        val refreshToken = authApplicationService.issueRefreshToken(user)

        authApplicationService.storeRefreshToken(refreshToken)
        tokenAccessor.injectTokenPair(response, ValidatedTokenPair(accessToken, refreshToken))

        val userProfile = userProfileFetcher.fetchUserProfile(user.id!!)

        val redirectUrl = UriComponentsBuilder
            .fromUriString(frontendCallbackUrl)
            .queryParam("isNewUser", false)
            .queryParam("accessToken", accessToken.value)
            .queryParam("email", user.username)
            .queryParam("name",userProfile.nickname)//여기
            .queryParam("profileImage", userProfile.profileImage)//여기
        .build().encode().toUriString()

        response.sendRedirect(redirectUrl)
    }
}