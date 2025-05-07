package dev.codeswamp.global.auth.infrastructure.jwt.service

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedAccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken
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
    override fun issueAccessToken(authUser: AuthUser): ValidatedAccessToken {
        TODO("not implemented")
    }

    //TODO 토큰 수명 별도 지정 필요
    override fun issueRefreshToken(authUser: AuthUser): ValidatedRefreshToken {
        TODO("not implemented")
    }
}