package dev.codeswamp.auth.application.acl

import dev.codeswamp.auth.application.dto.UserProfile
import org.springframework.stereotype.Service

@Service
class TempUserProfileFetcherImpl : UserProfileFetcher {
    override suspend fun fetchUserProfile(userId: Long) = UserProfile(
        nickname = "nickname",
        profileImage = ""
    )
}