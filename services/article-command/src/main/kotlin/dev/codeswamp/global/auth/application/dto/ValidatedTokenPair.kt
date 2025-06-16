package dev.codeswamp.global.auth.application.dto

import dev.codeswamp.global.auth.domain.model.token.ValidatedAccessToken
import dev.codeswamp.global.auth.domain.model.token.ValidatedRefreshToken

data class ValidatedTokenPair (
    val accessToken: ValidatedAccessToken,
    val refreshToken: ValidatedRefreshToken
)