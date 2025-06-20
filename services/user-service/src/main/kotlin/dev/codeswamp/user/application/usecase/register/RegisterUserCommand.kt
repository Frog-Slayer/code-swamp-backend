package dev.codeswamp.user.application.usecase.register

data class RegisterUserCommand (
    val token: String,
    val email: String,
    val username: String,
    val nickname: String,
    val profileImageUrl: String?,
)