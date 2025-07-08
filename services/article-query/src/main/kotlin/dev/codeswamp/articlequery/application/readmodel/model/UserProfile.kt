package dev.codeswamp.articlequery.application.readmodel.model

data class UserProfile (
    val userId: Long,
    val username: String,
    val nickname: String,
    val profileImage: String
)