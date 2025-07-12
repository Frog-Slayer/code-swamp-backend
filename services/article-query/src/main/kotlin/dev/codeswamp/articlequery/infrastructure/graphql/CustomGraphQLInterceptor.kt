package dev.codeswamp.articlequery.infrastructure.graphql

import org.springframework.graphql.server.WebGraphQlInterceptor
import org.springframework.graphql.server.WebGraphQlRequest
import org.springframework.graphql.server.WebGraphQlResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CustomGraphQLInterceptor : WebGraphQlInterceptor {
    override fun intercept(
        request: WebGraphQlRequest,
        chain: WebGraphQlInterceptor.Chain
    ): Mono<WebGraphQlResponse?> {
        val userAgent = request.headers["User-Agent"]?.first()

        val ipAddress = request.headers.getFirst("X-Real-IP")
            ?: request.remoteAddress?.address?.hostAddress
            ?: "unknown"

        println(request.headers.toSingleValueMap())
        println("userAgent: $userAgent, ipAddress: $ipAddress")
        request.configureExecutionInput({ _, builder ->
            builder.graphQLContext {
                it.of("ipAddress", ipAddress)
                    .of("userAgent", userAgent)
            }.build()
        })

        return chain.next(request)
    }
}
