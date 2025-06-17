package dev.codeswamp.auth.infrastructure.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import dev.codeswamp.auth.application.service.AuthApplicationService
import dev.codeswamp.auth.infrastructure.security.filter.TokenAuthenticationFilter
import dev.codeswamp.auth.infrastructure.security.handler.CustomLogoutHandler
import dev.codeswamp.auth.infrastructure.security.handler.CustomLogoutSuccessHandler
import dev.codeswamp.auth.infrastructure.security.oauth2.handler.OAuth2LoginFailureHandler
import dev.codeswamp.auth.infrastructure.security.oauth2.handler.OAuth2LoginSuccessHandler
import dev.codeswamp.auth.infrastructure.security.provider.TokenAuthenticationManager
import dev.codeswamp.auth.infrastructure.web.HttpTokenAccessor
import org.slf4j.LoggerFactory
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
class SecurityConfig (
    private val objectMapper: ObjectMapper,
    private val oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler,
    private val oAuth2LoginFailureHandler: OAuth2LoginFailureHandler,
    private val httpTokenAccessor: HttpTokenAccessor,
    private val customLogoutHandler: CustomLogoutHandler,
    private val customLogoutSuccessHandler: CustomLogoutSuccessHandler,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun skipPathList(): List<String> = listOf(
        "/users/signup",
        "/auth/refresh",
        "/auth/temp-login",
        "/oauth2/authorization/**",
        "/login/**",
        "/error/**",
        "/h2-console/**",
        "/actuator/**"
    )

    @Bean
    fun httpFilterChain(
        http: ServerHttpSecurity,
        skipPathList: List<String>,
        tokenAuthenticationManager: ReactiveAuthenticationManager,
    ): SecurityWebFilterChain {
        return http
            .csrf{ it.disable() }
            .cors {
                it.configurationSource { exchange ->
                    CorsConfiguration().apply {
                        allowedOrigins = listOf("http://localhost:3000")
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
            .logout { it
                .logoutUrl("/logout")
                .logoutHandler(customLogoutHandler)
                .logoutSuccessHandler(customLogoutSuccessHandler)
            }
            .authorizeExchange { it
                .pathMatchers(*skipPathList.toTypedArray()).permitAll()
                .anyExchange().authenticated()
            }
            .oauth2Login { it
                .authenticationSuccessHandler( oAuth2LoginSuccessHandler)
                .authenticationFailureHandler(oAuth2LoginFailureHandler)
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
                TokenAuthenticationFilter(tokenAuthenticationManager, httpTokenAccessor),
                SecurityWebFiltersOrder.LOGOUT
            )
            .build()
    }

    @Bean
    fun tokenAuthenticationManager(
        authApplicationService: AuthApplicationService
    ) = TokenAuthenticationManager(authApplicationService)
}