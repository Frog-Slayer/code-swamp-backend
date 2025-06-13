package dev.codeswamp.global.authgateway.application.dto

import dev.codeswamp.global.authgateway.domain.model.token.ValidatedAccessToken
import dev.codeswamp.global.authgateway.domain.model.token.ValidatedRefreshToken

data class ValidatedTokenPair (
    val accessToken: ValidatedAccessToken,
    val refreshToken: ValidatedRefreshToken
)