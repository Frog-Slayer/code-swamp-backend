package dev.codeswamp.global.authgateway.presentation.dto

import dev.codeswamp.global.authgateway.application.dto.UserProfile

data class AuthResult (
    val accessToken: String,
    val userProfile: UserProfile,
)