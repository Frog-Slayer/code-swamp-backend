package dev.codeswamp.authgateway.application.dto

import dev.codeswamp.authgateway.domain.model.token.ValidatedAccessToken
import dev.codeswamp.authgateway.domain.model.token.ValidatedRefreshToken

data class ValidatedTokenPair (
    val accessToken: ValidatedAccessToken,
    val refreshToken: ValidatedRefreshToken
)