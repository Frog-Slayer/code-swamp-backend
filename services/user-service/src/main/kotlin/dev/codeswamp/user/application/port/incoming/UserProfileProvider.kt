package dev.codeswamp.user.application.port.incoming

import dev.codeswamp.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

data class UserProfile (
    val nickname: String,
    val profileImage: String,
)

@Service
class UserProfileProvider(
    private val userRepository: UserRepository,
) {
    suspend fun provideUserProfile(userId : Long) : UserProfile {
        val user = userRepository.findById(userId) ?: throw RuntimeException("User with id $userId not found")

        return UserProfile(
            nickname= user.nickname.value,
            profileImage = user.profileUrl ?: ""
        )
    }
}