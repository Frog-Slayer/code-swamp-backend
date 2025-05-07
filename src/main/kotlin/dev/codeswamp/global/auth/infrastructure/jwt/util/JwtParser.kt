package dev.codeswamp.global.auth.infrastructure.jwt.util

import dev.codeswamp.global.auth.domain.model.authToken.RawAccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RawRefreshToken
import dev.codeswamp.global.auth.domain.util.TokenParser
import org.springframework.stereotype.Component

@Component
class JwtParser : TokenParser {
    override fun parseAccessToken(accessToken: String): RawAccessToken {
        TODO("Not yet implemented")
    }

    override fun parseRefreshToken(refreshToken: String): RawRefreshToken {
        TODO("Not yet implemented")
    }
}