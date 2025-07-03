package dev.codeswamp.authcommon.security

import com.fasterxml.jackson.databind.ObjectMapper
import dev.codeswamp.authcommon.token.JwtAccessTokenParser
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter
import org.springframework.web.cors.CorsConfiguration
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
class DefaultSecurityConfig(
    private val objectMapper: ObjectMapper,
    private val skipPathProvider: SkipPathProvider,
    @Value("\${frontend.url}") private val frontendUrl: String
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ConditionalOnMissingBean
    @Bean
    fun jwtAccessTokenParser(
        @Value("\${jwt.secret}") secret: String,
    ): JwtAccessTokenParser = JwtAccessTokenParser.fromSecret(secret)

    @ConditionalOnMissingBean
    @Bean
    fun tokenAuthenticationManager(
        jwtAccessTokenParser: JwtAccessTokenParser
    ) = TokenAuthenticationManager(jwtAccessTokenParser = jwtAccessTokenParser)


    @ConditionalOnMissingBean
    @Bean
    fun httpFilterChain(
        http: ServerHttpSecurity,
        tokenAuthenticationManager: ReactiveAuthenticationManager,
        authenticationEntryPoint: ServerAuthenticationEntryPoint,
    ): SecurityWebFilterChain {
        val skipPathList = skipPathProvider.skipPaths()
        return http
            .csrf { it.disable() }
            .cors {
                it.configurationSource { exchange ->
                    CorsConfiguration().apply {
                        allowedOrigins = listOf(frontendUrl)
                        allowedHeaders = listOf("*")
                        allowedMethods = listOf("*")
                        exposedHeaders = listOf("Authorization")
                        allowCredentials = true
                    }
                }
            }
            .headers {
                it.frameOptions { XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN }
            }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }
            .authorizeExchange {
                it
                    .pathMatchers(*skipPathList.toTypedArray()).permitAll()
                    .anyExchange().authenticated()
            }
            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint)
            }
            .addFilterBefore(
                TokenAuthenticationFilter(tokenAuthenticationManager, authenticationEntryPoint),
                SecurityWebFiltersOrder.LOGOUT
            )
            .build()
    }


    @Bean
    fun authenticationEntryPoint(): ServerAuthenticationEntryPoint {
        return ServerAuthenticationEntryPoint { exchange, authException ->
            val response = exchange.response
            response.statusCode = HttpStatus.UNAUTHORIZED
            logger.info("Authentication failed with: {}", response)
            response.writeWith(
                Mono.fromSupplier {
                    val errorResponseAsJson = objectMapper.writeValueAsBytes("Unauthorized")
                    response.bufferFactory().wrap(errorResponseAsJson)
                }
            )
        }
    }
}