package dev.codeswamp.auth.infrastructure.jwt.util

import dev.codeswamp.auth.domain.model.token.RawAccessToken
import dev.codeswamp.auth.domain.model.token.RawRefreshToken
import dev.codeswamp.auth.domain.service.TokenParser
import dev.codeswamp.authcommon.token.AccessTokenParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.temporal.ChronoUnit
import javax.crypto.SecretKey

@Component
class JwtParser (
    @Value("\${jwt.secret}") private val secret: String,
    private val accessTokenParser: AccessTokenParser,
) : TokenParser {

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    override fun parseAccessToken(accessToken: String): RawAccessToken {
        val payload = accessTokenParser.parse(accessToken)
        return RawAccessToken(
            value = accessToken,
            username = payload.sub,
            userId = payload.userId,
            roles = payload.role,
            expiration = payload.expiration,
        )
    }

    override fun parseRefreshToken(refreshToken: String): RawRefreshToken {
        val claims = Jwts.parser()
            .verifyWith(secretKey)
            .clockSkewSeconds(Long.MAX_VALUE / 1000)//ignore expiration here
            .build()
            .parseSignedClaims(refreshToken)
            .payload

        return RawRefreshToken(
            value = refreshToken,
            sub = claims.subject,
            expiration = claims.expiration.toInstant().truncatedTo(ChronoUnit.SECONDS)
        )
    }
}