package dev.codeswamp.auth.domain.service

import dev.codeswamp.auth.domain.model.AuthUser
import dev.codeswamp.auth.domain.model.token.ValidatedAccessToken
import dev.codeswamp.auth.domain.model.token.ValidatedRefreshToken

interface TokenGenerator {
    fun generateAccessToken(user: AuthUser): ValidatedAccessToken
    fun generateRefreshToken(user: AuthUser): ValidatedRefreshToken
}