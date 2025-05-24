package dev.codeswamp.core.user.application.dto

data class SignUpCommand (
    val userId: Long,
    val username: String,
    val nickname: String,
    val profileImageUrl: String?,
)