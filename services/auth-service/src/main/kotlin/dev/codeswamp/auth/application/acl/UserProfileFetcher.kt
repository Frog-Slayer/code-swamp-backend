package dev.codeswamp.auth.application.acl

import dev.codeswamp.auth.application.dto.UserProfile

interface UserProfileFetcher {
    suspend fun fetchUserProfile(userId: Long): UserProfile
}