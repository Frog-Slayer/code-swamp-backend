package dev.codeswamp.global.auth.presentation.dto

import dev.codeswamp.global.auth.application.dto.UserProfile

data class AuthResult (
    val accessToken: String,
    val userProfile: UserProfile,
)