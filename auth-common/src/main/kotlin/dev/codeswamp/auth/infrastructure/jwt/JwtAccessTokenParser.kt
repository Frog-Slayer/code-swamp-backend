package dev.codeswamp.auth.infrastructure.jwt

import dev.codeswamp.auth.domain.model.token.RawAccessToken
import dev.codeswamp.auth.domain.service.AccessTokenParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.temporal.ChronoUnit
import javax.crypto.SecretKey

class JwtAccessTokenParser (
    private val secretKey: SecretKey,
) : AccessTokenParser {

    override fun parseAccessToken(accessToken: String): RawAccessToken {
        val claims = Jwts.parser()
            .verifyWith(secretKey)
            .clockSkewSeconds(Long.MAX_VALUE / 1000)//ignore expiration here
            .build()
            .parseSignedClaims(accessToken)
            .payload

        return RawAccessToken(
            value = accessToken,
            username = claims.subject,
            userId = claims.subject.toLong(),
            expiration = claims.expiration.toInstant().truncatedTo(ChronoUnit.SECONDS)
        )
    }

    companion object {
        fun fromSecret(secret: String) : JwtAccessTokenParser {
            val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
            return JwtAccessTokenParser(key)
        }
    }
}