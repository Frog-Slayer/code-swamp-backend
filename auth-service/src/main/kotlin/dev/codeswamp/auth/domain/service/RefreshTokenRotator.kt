package dev.codeswamp.auth.domain.service

import dev.codeswamp.auth.domain.model.token.ValidatedRefreshToken
import dev.codeswamp.auth.domain.repository.TokenRepository
import org.springframework.stereotype.Service

@Service
class RefreshTokenRotator(
    private val tokenRepository: TokenRepository
){

    suspend fun rotate(newToken: ValidatedRefreshToken)
    {
        tokenRepository.findRefreshTokenByUserId(
            newToken.authUser.id ?: throw IllegalStateException("not a valid token"))
            ?.let { tokenRepository.delete(it) }

        tokenRepository.storeRefreshToken(newToken)
    }
}