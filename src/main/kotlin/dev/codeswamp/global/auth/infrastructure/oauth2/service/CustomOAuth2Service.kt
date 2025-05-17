package dev.codeswamp.global.auth.infrastructure.oauth2.service

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2Service: OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private val oAuth2Delegate = DefaultOAuth2UserService()
    private val oidcDelegate = OidcUserService()

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User? {
        return when (userRequest) {
            is OidcUserRequest -> oidcDelegate.loadUser(userRequest)
            else -> oAuth2Delegate.loadUser(userRequest)
        }
    }
}