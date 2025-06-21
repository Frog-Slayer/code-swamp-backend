package dev.codeswamp.auth.application.dto

import dev.codeswamp.auth.domain.model.token.ValidatedAccessToken
import dev.codeswamp.auth.domain.model.token.ValidatedRefreshToken

data class ValidatedTokenPair(
    val accessToken: ValidatedAccessToken,
    val refreshToken: ValidatedRefreshToken
)