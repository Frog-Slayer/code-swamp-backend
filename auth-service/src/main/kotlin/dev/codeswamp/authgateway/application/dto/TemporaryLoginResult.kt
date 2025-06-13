package dev.codeswamp.authgateway.application.dto

data class TemporaryLoginResult (
    val tokenPair: ValidatedTokenPair,
    val userProfile: UserProfile
)

