package dev.codeswamp.articlequery.application.port.outgoing

import dev.codeswamp.articlequery.application.readmodel.model.UserProfile

interface UserProfileFetcher {
    suspend fun fetchUserProfile(userId: Long): UserProfile
    suspend fun fetchUserProfiles(userIds: List<Long>): List<UserProfile>
}