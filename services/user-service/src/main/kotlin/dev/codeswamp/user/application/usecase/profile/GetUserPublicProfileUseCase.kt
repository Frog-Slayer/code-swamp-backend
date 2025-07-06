package dev.codeswamp.user.application.usecase.profile

interface GetUserPublicProfileUseCase {
    suspend fun getUserPublicProfile(username: String, fields: Set<String>) : UserPublicProfile
}