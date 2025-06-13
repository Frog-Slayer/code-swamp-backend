package dev.codeswamp.authgateway.application.acl

import dev.codeswamp.authgateway.application.dto.UserProfile
import org.springframework.stereotype.Service

@Service
class TempUserProfileFetcherImpl : UserProfileFetcher {
    override suspend fun fetchUserProfile(userId: Long) = UserProfile(
        nickname = "nickname",
        profileImage = ""
    )
}