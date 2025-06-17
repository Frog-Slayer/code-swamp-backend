package dev.codeswamp.auth.infrastructure.config

import dev.codeswamp.authcommon.token.AccessTokenParser
import dev.codeswamp.authcommon.token.JwtAccessTokenParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TokenParserConfig (
    @Value("\${jwt.secret}") private val secret: String,
) {
    @Bean
    fun accessTokenParser() : AccessTokenParser = JwtAccessTokenParser.fromSecret(secret)
}