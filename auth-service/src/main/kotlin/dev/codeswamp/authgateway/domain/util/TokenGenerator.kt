package dev.codeswamp.authgateway.domain.util

import dev.codeswamp.authcommon.domain.model.ValidatedAccessToken
import dev.codeswamp.authgateway.domain.model.AuthUser
import dev.codeswamp.authgateway.domain.model.token.ValidatedRefreshToken

interface TokenGenerator {
    fun generateAccessToken(user: AuthUser): ValidatedAccessToken
    fun generateRefreshToken(user: AuthUser): ValidatedRefreshToken
}