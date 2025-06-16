package dev.codeswamp.global.auth.domain.util

import dev.codeswamp.global.auth.domain.model.token.RawAccessToken
import dev.codeswamp.global.auth.domain.model.token.RawRefreshToken


interface TokenParser {
    fun parseAccessToken(accessToken: String): RawAccessToken
    fun parseRefreshToken(refreshToken: String): RawRefreshToken
}