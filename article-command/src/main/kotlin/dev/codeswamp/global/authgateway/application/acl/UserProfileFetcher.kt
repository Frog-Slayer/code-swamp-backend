package dev.codeswamp.global.authgateway.application.acl

import dev.codeswamp.global.authgateway.application.dto.UserProfile

interface UserProfileFetcher {
    fun fetchUserProfile(userId: Long): UserProfile
}