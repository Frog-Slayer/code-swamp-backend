package dev.codeswamp.authcommon.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import dev.codeswamp.authcommon.security.filter.TokenAuthenticationFilter
import dev.codeswamp.authcommon.security.provider.TokenAuthenticationManager
import dev.codeswamp.authcommon.token.JwtAccessTokenParser
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter
import org.springframework.web.cors.CorsConfiguration
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
class DefaultSecurityConfig(
    private val objectMapper: ObjectMapper,
    @Value("\${frontend.url}") private val frontendUrl: String
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun skipPathList(): List<String> = listOf(
        "/error/**",
        "/h2-console/**",
        "/actuator/**"
    )

    @Bean
    fun jwtAccessTokenParser(
        @Value("\${jwt.secret}") secret: String,
    ): JwtAccessTokenParser = JwtAccessTokenParser.fromSecret(secret)

    @Bean
    fun tokenAuthenticationManager(
        jwtAccessTokenParser: JwtAccessTokenParser
    ) = TokenAuthenticationManager(jwtAccessTokenParser = jwtAccessTokenParser)


    @Bean
    fun httpFilterChain(
        http: ServerHttpSecurity,
        skipPathList: List<String>,
        tokenAuthenticationManager: ReactiveAuthenticationManager,
    ): SecurityWebFilterChain {
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
                it.authenticationEntryPoint { exchange, authException ->
                    val response = exchange.response
                    response.statusCode = HttpStatus.UNAUTHORIZED
                    response.writeWith(
                        Mono.fromSupplier {
                            val errorResponseAsJson = objectMapper.writeValueAsBytes("Unauthorized")
                            response.bufferFactory().wrap(errorResponseAsJson)
                        }
                    )
                }
            }
            .addFilterBefore(
                TokenAuthenticationFilter(tokenAuthenticationManager),
                SecurityWebFiltersOrder.LOGOUT
            )
            .build()
    }


}