package dev.codeswamp.auth.presentation.dto

import dev.codeswamp.auth.application.dto.UserProfile

data class AuthResult (
    val accessToken: String,
    val userProfile: UserProfile,
)