package dev.codeswamp.articlequery.application.port.outgoing

import dev.codeswamp.articlequery.application.dto.UserProfile

interface UserProfileFetcher {
    suspend fun fetchUserProfiles(userIds: List<Long>): List<UserProfile>
}