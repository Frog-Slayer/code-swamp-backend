package dev.codeswamp.user.infrastructure.auth

import dev.codeswamp.authcommon.token.AccessTokenParser
import dev.codeswamp.authcommon.token.JwtAccessTokenParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class AuthConfig(
    @Value("\${jwt.secret}") private val secret: String,
) {
    @Bean
    fun accessTokenParser(): AccessTokenParser = JwtAccessTokenParser.fromSecret(secret)
}