package dev.codeswamp.auth.domain.service

import dev.codeswamp.auth.domain.model.token.RawAccessToken

interface AccessTokenParser {
    fun parseAccessToken(accessToken: String): RawAccessToken
}