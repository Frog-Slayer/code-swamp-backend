package dev.codeswamp.global.auth.domain.util

import dev.codeswamp.global.auth.domain.model.AuthUser

interface TokenGenerator {
    fun generateAccessToken(user: AuthUser): String
    fun generateRefreshToken(user: AuthUser): String
}