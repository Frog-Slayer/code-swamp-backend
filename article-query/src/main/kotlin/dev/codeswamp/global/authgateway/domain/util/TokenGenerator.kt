package dev.codeswamp.global.authgateway.domain.util

import dev.codeswamp.global.authgateway.domain.model.AuthUser
import dev.codeswamp.global.authgateway.domain.model.token.ValidatedAccessToken
import dev.codeswamp.global.authgateway.domain.model.token.ValidatedRefreshToken

interface TokenGenerator {
    fun generateAccessToken(user: AuthUser): ValidatedAccessToken
    fun generateRefreshToken(user: AuthUser): ValidatedRefreshToken
}