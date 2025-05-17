package dev.codeswamp.global.auth.infrastructure.oauth2.client

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GithubClient (
    @Qualifier("githubWebClient") private val webClient: WebClient,
) {
    data class GithubEmailResponse (
        val email: String,
        val verified: Boolean,
        val primary: Boolean,
        val visibility: String?
    )
    companion object {
        val emailResponseTypeRef = object : ParameterizedTypeReference<List<GithubEmailResponse>>() {}
    }

    fun fetchPrimaryEmail(accessToken: String): String? {
        return webClient.get()
            .uri("/user/emails")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
            .retrieve()
            .bodyToMono(emailResponseTypeRef )
            .mapNotNull { emailList ->
                emailList.firstOrNull { it.primary && it.verified }?.email
            }
            .block()
    }
}