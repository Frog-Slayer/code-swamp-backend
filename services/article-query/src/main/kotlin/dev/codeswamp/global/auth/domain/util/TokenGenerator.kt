package dev.codeswamp.global.auth.domain.util

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.token.ValidatedAccessToken
import dev.codeswamp.global.auth.domain.model.token.ValidatedRefreshToken

interface TokenGenerator {
    fun generateAccessToken(user: AuthUser): ValidatedAccessToken
    fun generateRefreshToken(user: AuthUser): ValidatedRefreshToken
}