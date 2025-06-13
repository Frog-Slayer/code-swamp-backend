package dev.codeswamp.auth.infrastructure.jwt.util

import dev.codeswamp.auth.domain.model.AuthUser
import dev.codeswamp.auth.domain.model.token.ValidatedAccessToken
import dev.codeswamp.auth.domain.model.token.ValidatedRefreshToken
import dev.codeswamp.auth.domain.service.TokenGenerator
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
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
            .content("userId", user.id.toString())
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(accessTokenExpiration)))
            .signWith(secretKey)
            .compact()

        return ValidatedAccessToken(
            value = value,
            authUser = user,
            expiration = now.plusSeconds(accessTokenExpiration).truncatedTo(ChronoUnit.SECONDS),
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
            expiration = now.plusSeconds(refreshTokenExpiration).truncatedTo(ChronoUnit.SECONDS),
        )
    }
}