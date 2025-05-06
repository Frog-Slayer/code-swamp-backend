package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.authToken.AccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RefreshToken
import org.springframework.stereotype.Service

@Service
class TokenService (
    private val tokenIssueService: TokenIssueService,
    private val tokenValidationService: TokenValidationService,
    private val tokenStoreService: TokenStoreService,
) : TokenIssueService by tokenIssueService, TokenValidationService by tokenValidationService , TokenStoreService by tokenStoreService