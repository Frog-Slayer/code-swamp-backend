package dev.codeswamp.core.user.presentation.dto.request

data class UserSignUpRequestDto (
    val username: String,
    val nickname: String,
    val profileImageUrl: String? = null
)
