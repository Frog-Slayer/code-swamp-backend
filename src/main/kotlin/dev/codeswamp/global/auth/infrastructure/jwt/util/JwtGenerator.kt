package dev.codeswamp.global.auth.infrastructure.jwt.util

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedAccessToken
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken
import dev.codeswamp.global.auth.domain.util.TokenGenerator
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtGenerator(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token-exp}") private val accessTokenExpiration: Long,
    @Value("\${jwt.access-token-exp}") private val refreshTokenExpiration: Long,
): TokenGenerator {

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    override fun generateAccessToken(user: AuthUser): ValidatedAccessToken {
        val now = Instant.now()
        val value = Jwts.builder()
            .subject(user.username)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(accessTokenExpiration)))
            .signWith(secretKey)
            .compact()

        return ValidatedAccessToken(
            value = value,
            authUser = user,
            expiration = now.plusSeconds(accessTokenExpiration)
        )
    }

    override fun generateRefreshToken(user: AuthUser): ValidatedRefreshToken {
        val now = Instant.now()
        val value = Jwts.builder()
            .subject(user.username) //TODO
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(refreshTokenExpiration)))
            .signWith(secretKey)
            .compact()

        return ValidatedRefreshToken(
            value = value,
            authUser = user,
            expiration = now.plusSeconds( refreshTokenExpiration)
        )
    }
}