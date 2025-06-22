package dev.codeswamp.auth.infrastructure.security.oauth2.client

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class GithubClient(
    @Qualifier("githubWebClient") private val webClient: WebClient,
) {
    data class GithubEmailResponse(
        val email: String,
        val verified: Boolean,
        val primary: Boolean,
        val visibility: String?
    )

    companion object {
        val emailResponseTypeRef = object : ParameterizedTypeReference<List<GithubEmailResponse>>() {}
    }

    suspend fun fetchPrimaryEmail(accessToken: String): String? {
        return webClient.get()
            .uri("/user/emails")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
            .retrieve()
            .onStatus({ it.is4xxClientError || it.is5xxServerError }) { response ->
                response.bodyToMono(String::class.java).flatMap {
                    Mono.error(RuntimeException("Cannot fetch primary email from github. Error Code: $it"))
                }
            }
            .bodyToMono(emailResponseTypeRef)
            .awaitSingle()
            .firstOrNull { it.primary && it.verified }?.email
    }
}