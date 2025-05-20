package dev.codeswamp.core.user.infrastructure.auth

import dev.codeswamp.core.user.domain.service.UserService
import dev.codeswamp.global.auth.application.dto.UserProfile
import dev.codeswamp.global.auth.application.service.UserProfileFetcher
import org.springframework.stereotype.Component

@Component
class UserProfileFetcherImpl(
    private val userService: UserService,
): UserProfileFetcher {
    override fun fetchUserProfile(userId: Long): UserProfile {
        val user = userService.findById(userId) ?: throw Exception("User not found")
        return UserProfile(
            nickname = user.nickname.value!!,
            profileImage = user.profileUrl ?: ""
        )
    }
}