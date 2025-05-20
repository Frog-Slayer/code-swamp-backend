package dev.codeswamp.global.auth.infrastructure.oauth2.handler

import dev.codeswamp.global.auth.application.service.UserProfileFetcher
import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.service.AuthUserService
import dev.codeswamp.global.auth.domain.service.TokenService
import dev.codeswamp.global.auth.infrastructure.oauth2.dto.ProviderUserInfo
import dev.codeswamp.global.auth.infrastructure.oauth2.factory.UserInfoFactory
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
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
    private val tokenService: TokenService,
    private val userProfileFetcher: UserProfileFetcher,
    private val authUserService: AuthUserService,
    private val userInfoFactory: UserInfoFactory,
    private val authorizedClientService: OAuth2AuthorizedClientService
) : AuthenticationSuccessHandler {

    val frontendCallbackUrl: String = "http://localhost:3000/auth/callback"

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
        val authUser = authUserService.findByUsername(providerUserInfo.email)

        if  (authUser == null) {
            handleNewUser(response, providerUserInfo)
            return;
        }

        handleRegisteredUserAndPassTokens(response, authUser)
    }

    private fun handleNewUser(response: HttpServletResponse, userInfo: ProviderUserInfo) {
        val redirectUrl = UriComponentsBuilder
            .fromUriString(frontendCallbackUrl)
            .queryParam("isNewUser", true)
            .queryParam("email", userInfo.email)
            .queryParam("name", userInfo.name)
            .queryParam("profileImage",userInfo.profileImage)
            .build().toUriString()

        response.sendRedirect(redirectUrl)
        return;
    }

    //TODO: ProviderUserInfo가 아니라, 저장된 정보를 가지고 와야 함
    private fun handleRegisteredUserAndPassTokens(response: HttpServletResponse, user: AuthUser) {
        val accessToken = tokenService.issueAccessToken(user)
        val userProfile = userProfileFetcher.fetchUserProfile(user.id!!)

        val redirectUrl = UriComponentsBuilder
            .fromUriString(frontendCallbackUrl)
            .queryParam("isNewUser", false)
            .queryParam("accessToken", accessToken)
            .queryParam("email", user.username)
            .queryParam("name",userProfile.nickname)//여기
            .queryParam("profileImage", userProfile.profileImage)//여기
        .build().toUriString()

        response.sendRedirect(redirectUrl)
    }
}