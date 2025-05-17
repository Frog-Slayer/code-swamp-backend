package dev.codeswamp.global.auth.infrastructure.oauth2.dto

class GithubUserInfo(private val attributes: Map<String, Any>, fetchedEmail: String) : ProviderUserInfo {
    override val email = fetchedEmail
    override val name = attributes["name"] as String
    override val provider = "github"
}
