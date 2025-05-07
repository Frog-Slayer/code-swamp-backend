package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedAccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken

interface TokenIssueService {
    fun issueAccessToken(authUser: AuthUser) : ValidatedAccessToken
    fun issueRefreshToken(authUser: AuthUser) : ValidatedRefreshToken
}