package dev.codeswamp.authgateway.application.acl

import dev.codeswamp.authgateway.application.dto.UserProfile

interface UserProfileFetcher {
    suspend fun fetchUserProfile(userId: Long): UserProfile
}