package dev.codeswamp.global.auth.infrastructure.oauth2.factory

import dev.codeswamp.global.auth.infrastructure.oauth2.dto.GitHubUserInfo
import dev.codeswamp.global.auth.infrastructure.oauth2.dto.GoogleUserInfo
import org.springframework.security.oauth2.core.user.OAuth2User

interface ProviderUserInfo {
    val email: String
    val name: String
    val provider: String
}

object UserInfoFactory {
    fun extract(
        registrationId: String,
        user: OAuth2User
    ): ProviderUserInfo {
        return when (registrationId) {
            "google" -> GoogleUserInfo(user.attributes)
            "github" -> GitHubUserInfo(user.attributes)
            else -> throw IllegalArgumentException("Unsupported provider: $registrationId")
        }
    }


}