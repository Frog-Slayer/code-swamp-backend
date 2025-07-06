package dev.codeswamp.user.application.usecase.profile

data class UserPublicProfile(
    val userId: Long?,
    val username: String?,
    val nickname: String?,
    val profileImage: String?
)