package dev.codeswamp.global.auth.domain.repository

import dev.codeswamp.global.auth.domain.model.AuthToken
import java.util.Optional

interface AuthTokenRepository {
    fun saveToken(userId: Long, authToken: AuthToken)
    fun findByUserId(userId: Long): Optional<AuthToken>
    fun deleteByUserId(userId: Long)
}