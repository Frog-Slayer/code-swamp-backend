package dev.codeswamp.auth.application.dto

data class UserProfile(
    val userId: Long,
    val username: String,
    val nickname: String,
    val profileImage: String
)