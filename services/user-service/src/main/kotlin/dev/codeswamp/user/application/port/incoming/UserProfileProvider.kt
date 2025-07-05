package dev.codeswamp.user.application.port.incoming

import dev.codeswamp.user.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

data class UserProfile(
    val userId : Long,
    val username: String,
    val nickname: String,
    val profileImage: String,
)

@Service
class UserProfileProvider(
    private val userRepository: UserRepository,
) {
    suspend fun provideUserProfile(userId: Long): UserProfile {
        val user = userRepository.findById(userId) ?: throw RuntimeException("User with id $userId not found")

        return UserProfile(
            userId = user.id,
            username = user.username.value,
            nickname = user.nickname.value,
            profileImage = user.profileImage ?: ""
        )
    }

    suspend fun provideUserProfiles(userIds: List<Long>): List<UserProfile> {
        val users = userRepository.findAllByIdsIn(userIds)

        return users.map {  it ->
            UserProfile(
                userId = it.id,
                username = it.username.value,
                nickname = it.nickname.value,
                profileImage = it.profileImage ?: ""
            )
        }
    }
}