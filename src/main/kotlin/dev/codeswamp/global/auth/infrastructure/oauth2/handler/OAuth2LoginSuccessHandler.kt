package dev.codeswamp.global.auth.infrastructure.oauth2.handler

import com.fasterxml.jackson.databind.ObjectMapper
import dev.codeswamp.global.auth.domain.service.TokenService
import dev.codeswamp.global.auth.domain.service.UserFinder
import dev.codeswamp.global.auth.infrastructure.oauth2.factory.UserInfoFactory
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler(
    private val userFinder: UserFinder,
    private val objectMapper: ObjectMapper,
    private val tokenService: TokenService,
    private val userInfoFactory: UserInfoFactory,
    private val authorizedClientService: OAuth2AuthorizedClientService
) : AuthenticationSuccessHandler {
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

        println(oAuth2User.attributes)

        val userInfo = userInfoFactory.extract(registrationId, oAuth2User, oauth2AccessToken)
        val user = userFinder.findByEmail(userInfo.email)

        if  (user == null) {
            response?.status = 401
            response.contentType = "application/json"
            response.writer.write(objectMapper.writeValueAsString(userInfo))
            return;
        }

        //TODO -> 별도의 로직으로 분리
        val accessToken = tokenService.issueAccessToken(user)
        val refreshToken = tokenService.issueRefreshToken(user)

        response.setHeader("Authorization", "Bearer $accessToken")
        val refreshTokenCookie = Cookie("refresh_token", refreshToken.value).apply {
            path = "/"
            isHttpOnly = true
            secure = true
            maxAge = 60 * 60 * 24 * 14
        }

        response.addCookie(refreshTokenCookie)
    }
}