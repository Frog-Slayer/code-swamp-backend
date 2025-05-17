package dev.codeswamp.global.auth.infrastructure.oauth2.dto

import dev.codeswamp.global.auth.infrastructure.oauth2.factory.ProviderUserInfo

class GitHubUserInfo(private val attributes: Map<String, Any>) : ProviderUserInfo {
    override val email = attributes["email"] as String
    override val name = attributes["login"] as String
    override val provider = "github"
}

