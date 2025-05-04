package dev.codeswamp.global.auth.domain.model

data class AuthToken (
    val value: String,
    val accessTokenExpiration: Long,
)