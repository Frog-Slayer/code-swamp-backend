package dev.codeswamp.authgateway.presentation.dto

import dev.codeswamp.authgateway.application.dto.UserProfile

data class AuthResult (
    val accessToken: String,
    val userProfile: UserProfile,
)