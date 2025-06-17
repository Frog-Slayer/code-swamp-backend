package dev.codeswamp.authcommon.token

import dev.codeswamp.authcommon.exception.InvalidTokenException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.time.temporal.ChronoUnit
import javax.crypto.SecretKey

class JwtAccessTokenParser (
    private val secretKey: SecretKey,
) : AccessTokenParser {

    override fun parse (token: String): AccessTokenPayload{
        val claims = Jwts.parser()
            .verifyWith(secretKey)
            .clockSkewSeconds(Long.MAX_VALUE / 1000)//ignore expiration here
            .build()
            .parseSignedClaims(token)
            .payload


        val userId = when (val rawId = claims["userId"]) {
            is Number -> rawId.toLong()
            is String -> rawId.toLongOrNull()
            else -> null
        }

        if (userId == null) throw InvalidTokenException("Cannot parse userId")

        val role = when (val rawRole = claims["role"]) {
            is List<*> -> rawRole.filterIsInstance<String>()
            is String -> listOf(rawRole)
            else -> emptyList()
        }

        if (role.isEmpty()) throw InvalidTokenException("Cannot parse role")

        return AccessTokenPayload(
                sub = claims.subject,
                userId = userId,
                role = role,
                issuedAt = claims.issuedAt.toInstant().truncatedTo(ChronoUnit.SECONDS),
                expiration = claims.expiration.toInstant().truncatedTo(ChronoUnit.SECONDS),
        )
    }

    companion object {
        fun fromSecret(secret: String) : JwtAccessTokenParser {
            val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
            return JwtAccessTokenParser(key)
        }
    }
}