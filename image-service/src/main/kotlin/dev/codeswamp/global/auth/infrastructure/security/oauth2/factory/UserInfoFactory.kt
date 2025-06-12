package dev.codeswamp.global.auth.infrastructure.security.oauth2.factory

import dev.codeswamp.global.auth.infrastructure.security.oauth2.client.GithubClient
import dev.codeswamp.global.auth.infrastructure.security.oauth2.dto.GithubUserInfo
import dev.codeswamp.global.auth.infrastructure.security.oauth2.dto.GoogleUserInfo
import dev.codeswamp.global.auth.infrastructure.security.oauth2.dto.ProviderUserInfo
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component

@Component
class UserInfoFactory (
    private val githubClient: GithubClient
){
    fun extract(
        registrationId: String,
        user: OAuth2User,
        accessToken: String,
    ): ProviderUserInfo {
        return when (registrationId) {
            "google" -> GoogleUserInfo(user.attributes)
            "github" -> GithubUserInfo(user.attributes, githubClient.fetchPrimaryEmail(accessToken)!!)
            else -> throw IllegalArgumentException("Unsupported provider: $registrationId")
        }
    }
}