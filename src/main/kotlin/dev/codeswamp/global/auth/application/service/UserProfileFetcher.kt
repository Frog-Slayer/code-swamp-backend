package dev.codeswamp.global.auth.application.service

import dev.codeswamp.global.auth.application.dto.UserProfile

interface UserProfileFetcher {
    fun fetchUserProfile(userId: Long): UserProfile
}