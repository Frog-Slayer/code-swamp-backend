package dev.codeswamp.global.auth.infrastructure.jwt.service

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.authToken.AccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RefreshToken
import dev.codeswamp.global.auth.domain.service.TokenIssueService
import io.jsonwebtoken.Jwts
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

@Primary
@Service
class JwtIssuer : TokenIssueService{
    //TODO 토큰 수명 별도 지정 필요
    override fun issueAccessToken(authUser: AuthUser): AccessToken {
        val accessToken = Jwts.builder()
            .subject(authUser.username)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + 3600 * 24 * 1000))
            .compact();

        return AccessToken(
            value = accessToken,
            authUser = authUser,
            expiration = Instant.now().plus(3600 * 24, ChronoUnit.SECONDS)
        )
    }

    //TODO 토큰 수명 별도 지정 필요
    override fun issueRefreshToken(authUser: AuthUser): RefreshToken {
        val refreshToken= Jwts.builder()
            .subject(authUser.username)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + 3600 * 24 * 1000))
            .compact();

        return RefreshToken(
            value = refreshToken,
            authUser = authUser,
            expiration = Instant.now().plus(3600 * 24, ChronoUnit.SECONDS)
        )
    }
}