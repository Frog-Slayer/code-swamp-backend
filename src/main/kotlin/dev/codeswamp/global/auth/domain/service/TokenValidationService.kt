package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.authToken.AccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RefreshToken

interface TokenValidationService {
    fun validateAccessToken(accessToken: AccessToken)
    fun validateRefreshToken(refreshToken: RefreshToken)
}