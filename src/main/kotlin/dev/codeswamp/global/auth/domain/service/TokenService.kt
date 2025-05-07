package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.authToken.ValidatedAccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken
import org.springframework.stereotype.Service

@Service
class TokenService (
    private val tokenIssueService: TokenIssueService,
    private val tokenValidationService: TokenValidationService,
    private val tokenStoreService: TokenStoreService,
) : TokenIssueService by tokenIssueService, TokenValidationService by tokenValidationService , TokenStoreService by tokenStoreService {

    fun refreshAccessToken(refreshToken: ValidatedRefreshToken): ValidatedAccessToken {
        TODO("not yet implemented")
    }
}