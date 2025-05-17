package dev.codeswamp.global.auth.infrastructure.oauth2.dto

import dev.codeswamp.global.auth.infrastructure.oauth2.factory.ProviderUserInfo

class GoogleUserInfo(private val attributes: Map<String, Any>) : ProviderUserInfo {
    override val email = attributes["email"] as String
    override val name = attributes["name"] as String
    override val provider = "google"
}
