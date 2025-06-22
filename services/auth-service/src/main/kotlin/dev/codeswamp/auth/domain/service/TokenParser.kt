package dev.codeswamp.auth.domain.service

import dev.codeswamp.auth.domain.model.token.RawAccessToken
import dev.codeswamp.auth.domain.model.token.RawRefreshToken


interface TokenParser {
    fun parseAccessToken(accessToken: String): RawAccessToken
    fun parseRefreshToken(refreshToken: String): RawRefreshToken
}