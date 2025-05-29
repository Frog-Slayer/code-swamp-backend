package dev.codeswamp.global.auth.infrastructure.security.config

import dev.codeswamp.global.auth.application.service.AuthApplicationService
import dev.codeswamp.global.auth.infrastructure.security.filter.TokenAuthenticationFilter
import dev.codeswamp.global.auth.infrastructure.security.handler.CustomLogoutHandler
import dev.codeswamp.global.auth.infrastructure.security.handler.CustomLogoutSuccessHandler
import dev.codeswamp.global.auth.infrastructure.security.oauth2.service.CustomOAuth2Service
import dev.codeswamp.global.auth.infrastructure.security.oauth2.handler.OAuth2LoginFailureHandler
import dev.codeswamp.global.auth.infrastructure.security.oauth2.handler.OAuth2LoginSuccessHandler
import dev.codeswamp.global.auth.infrastructure.security.provider.TokenAuthenticationProvider
import dev.codeswamp.global.auth.infrastructure.security.util.FilterSkipMatcher
import dev.codeswamp.global.auth.infrastructure.web.HttpTokenAccessor
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val customOAuth2Service: CustomOAuth2Service,
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
        "/h2-console/**"
    )

    @Bean
    fun corsConfigurationSource() = UrlBasedCorsConfigurationSource().apply {
        registerCorsConfiguration("/**", CorsConfiguration().apply {
           allowedOrigins = listOf("http://localhost:3000")
            allowedHeaders = listOf("*")
            allowedMethods = listOf("*")
            exposedHeaders = listOf("Authorization")
            allowCredentials = true
        })
    }

    @Bean
    fun httpFilterChain(
        http: HttpSecurity,
        skipPathList: List<String>,
        authenticationManager: AuthenticationManager
    ): SecurityFilterChain? {

        val filterSkipMatcher = FilterSkipMatcher(skipPathList)

        http
            .csrf{ it.disable() }
            .cors {}
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .headers { it.frameOptions { frame -> frame.sameOrigin() } }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout {
                it.logoutUrl("/logout")
                    .addLogoutHandler(customLogoutHandler)
                    .logoutSuccessHandler(customLogoutSuccessHandler)
            }
            .authorizeHttpRequests {
                it.requestMatchers(*skipPathList.toTypedArray()).permitAll()
                it.anyRequest().authenticated()
            }
            .oauth2Login {
                it.userInfoEndpoint { it ->
                    it.userService(customOAuth2Service) // OAuth2UserService 주입
                }
                it.successHandler( oAuth2LoginSuccessHandler)
                it.failureHandler(oAuth2LoginFailureHandler)
            }
            .exceptionHandling {
                logger.warn("auth failed")

                it.authenticationEntryPoint { request, response, authException ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
            }
        }

        logger.debug("authenticationManager {}", authenticationManager.javaClass)
        logger.debug("provider {}", (authenticationManager as? ProviderManager)?.providers)

        http.addFilterBefore(tokenAuthenticationFilter( filterSkipMatcher, authenticationManager),
            LogoutFilter::class.java)

        return http.build()
    }

    @Bean
    fun authenticationManager(authApplicationService: AuthApplicationService):  AuthenticationManager =
        ProviderManager(TokenAuthenticationProvider(authApplicationService))

    fun tokenAuthenticationFilter(requestMatcher: RequestMatcher, authenticationManager: AuthenticationManager)
    : TokenAuthenticationFilter {
        val filter = TokenAuthenticationFilter(requestMatcher, httpTokenAccessor)
        filter.setAuthenticationManager(authenticationManager)
        return filter
    }
}