package dev.codeswamp.auth.infrastructure.security.oauth2.dto

class GithubUserInfo(private val attributes: Map<String, Any>, fetchedEmail: String) : ProviderUserInfo {
    override val email = fetchedEmail
    override val name = attributes["name"] as String
    override val profileImage = attributes["avatar_url"] as String
    override val provider = "github"
}
