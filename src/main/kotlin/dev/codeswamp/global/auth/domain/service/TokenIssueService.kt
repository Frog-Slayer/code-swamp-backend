package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.authToken.AccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RefreshToken

interface TokenIssueService {
    fun issueAccessToken(authUser: AuthUser) : AccessToken
    fun issueRefreshToken(authUser: AuthUser) : RefreshToken
}