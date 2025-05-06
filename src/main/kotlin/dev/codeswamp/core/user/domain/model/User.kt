package dev.codeswamp.core.user.domain.model

import javax.management.relation.Role

data class User (
    val id: Long? = null,
    val username: String,//변경 불가(사용자 ID)
    val email: String,
    val nickname: String,
    val profileUrl: String,
    val role: Role,
)