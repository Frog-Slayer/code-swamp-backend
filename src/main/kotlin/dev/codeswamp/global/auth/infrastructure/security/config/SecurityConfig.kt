package dev.codeswamp.global.auth.infrastructure.security.config

import dev.codeswamp.global.auth.infrastructure.security.oauth2.service.CustomOAuth2Service
import dev.codeswamp.global.auth.infrastructure.security.oauth2.handler.OAuth2LoginFailureHandler
import dev.codeswamp.global.auth.infrastructure.security.oauth2.handler.OAuth2LoginSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val customOAuth2Service: CustomOAuth2Service,
    private val oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler,
    private val oAuth2LoginFailureHandler: OAuth2LoginFailureHandler,
) {
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfig = CorsConfiguration().apply {
            allowedOrigins = listOf("http://localhost:3000")
            allowedHeaders = listOf("*")
            allowedMethods = listOf("*")
            exposedHeaders = listOf("Authorization")
            allowCredentials = true
        }

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)
        return source
    }

    @Bean
    fun httpFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .csrf{ it.disable() }
            .cors {}
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .authorizeHttpRequests {//TODO
                it.requestMatchers(AntPathRequestMatcher("/**")).permitAll()
                it.anyRequest().authenticated()
            }
            .oauth2Login {
                it.userInfoEndpoint { it ->
                    it.userService(customOAuth2Service) // OAuth2UserService 주입
                }
                it.successHandler( oAuth2LoginSuccessHandler)
                it.failureHandler(oAuth2LoginFailureHandler)
            }

        return http.build()
    }
}