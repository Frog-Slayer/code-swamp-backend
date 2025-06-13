package dev.codeswamp.authgateway.domain.util

import dev.codeswamp.authgateway.domain.model.token.RawAccessToken
import dev.codeswamp.authgateway.domain.model.token.RawRefreshToken


interface TokenParser {
    fun parseAccessToken(accessToken: String): RawAccessToken
    fun parseRefreshToken(refreshToken: String): RawRefreshToken
}