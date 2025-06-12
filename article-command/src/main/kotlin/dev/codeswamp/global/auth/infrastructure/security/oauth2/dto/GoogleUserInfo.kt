package dev.codeswamp.global.auth.infrastructure.security.oauth2.dto

class GoogleUserInfo(private val attributes: Map<String, Any>) : ProviderUserInfo {
    override val email = attributes["email"] as String
    override val name = attributes["name"] as String
    override val profileImage = attributes["picture"] as String
    override val provider = "google"
}

