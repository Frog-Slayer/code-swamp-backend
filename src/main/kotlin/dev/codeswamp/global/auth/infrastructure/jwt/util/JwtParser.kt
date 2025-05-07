package dev.codeswamp.global.auth.infrastructure.jwt.util

import dev.codeswamp.global.auth.domain.model.authToken.RawAccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RawRefreshToken
import dev.codeswamp.global.auth.domain.util.TokenParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.Base64
import javax.crypto.SecretKey

@Component
class JwtParser : TokenParser {
    private val jwtKey = "temporary_jwt_auth_key"
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtKey))

    override fun parseAccessToken(accessToken: String): RawAccessToken {
        val claims = Jwts.parser()
            .verifyWith(secretKey)
            .clockSkewSeconds(Long.MAX_VALUE)//ignore expiration here
            .build()
            .parseSignedClaims(accessToken)
            .payload

        return RawAccessToken(
            value = accessToken,
            sub = claims.subject,
            expiration = claims.expiration.toInstant()
        )
    }

    override fun parseRefreshToken(refreshToken: String): RawRefreshToken {
        val claims = Jwts.parser()
            .verifyWith(secretKey)
            .clockSkewSeconds(Long.MAX_VALUE)//ignore expiration here
            .build()
            .parseSignedClaims(refreshToken)
            .payload

        return RawRefreshToken(
            value = refreshToken,
            sub = claims.subject,
            expiration = claims.expiration.toInstant()
        )
    }
}