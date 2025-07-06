package dev.codeswamp.user.application.usecase.profile

interface UserPublicProfileQueryDao {
    suspend fun findByUserNameWithFields(username: String, fields: Set<String>) : UserPublicProfile?
}