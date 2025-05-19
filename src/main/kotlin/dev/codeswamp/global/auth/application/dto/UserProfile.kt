package dev.codeswamp.global.auth.application.dto

data class UserProfile (
    val email: String,
    val nickname: String,
    val profileImage: String
)