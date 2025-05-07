package dev.codeswamp.global.auth.infrastructure.adapter.jwt.service

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.authToken.AccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RefreshToken
import dev.codeswamp.global.auth.domain.service.TokenIssueService
import dev.codeswamp.global.auth.domain.service.TokenStoreService
import io.jsonwebtoken.Jwts
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

@Primary
@Service
class JwtStore : TokenStoreService{
    override fun storeRefreshToken(token: RefreshToken) {
        TODO("Not yet implemented")
    }

    override fun rotateRefreshToken(token: String, newToken: RefreshToken) {
        TODO("Not yet implemented")
    }

    override fun findRefreshTokenByUserId(userId: Long): RefreshToken? {
        TODO("Not yet implemented")
    }

}