package dev.codeswamp.global.authgateway.domain.util

import dev.codeswamp.global.authgateway.domain.model.token.RawAccessToken
import dev.codeswamp.global.authgateway.domain.model.token.RawRefreshToken


interface TokenParser {
    fun parseAccessToken(accessToken: String): RawAccessToken
    fun parseRefreshToken(refreshToken: String): RawRefreshToken
}