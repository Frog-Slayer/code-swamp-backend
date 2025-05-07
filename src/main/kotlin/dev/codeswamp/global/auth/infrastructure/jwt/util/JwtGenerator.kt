package dev.codeswamp.global.auth.infrastructure.jwt.util

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.util.TokenGenerator
import org.springframework.stereotype.Component

@Component
class JwtGenerator : TokenGenerator{
    override fun generateAccessToken(user: AuthUser): String {
        TODO("Not yet implemented")
    }

    override fun generateRefreshToken(user: AuthUser): String {
        TODO("Not yet implemented")
    }
}