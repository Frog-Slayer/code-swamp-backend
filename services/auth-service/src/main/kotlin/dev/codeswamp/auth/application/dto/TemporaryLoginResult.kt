package dev.codeswamp.auth.application.dto

data class TemporaryLoginResult (
    val tokenPair: ValidatedTokenPair,
    val userProfile: UserProfile
)

