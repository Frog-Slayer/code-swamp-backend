package dev.codeswamp.user.application.usecase.profile

import dev.codeswamp.user.application.exception.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class GetUserPublicProfileUseCaseImpl(
    private val userPublicProfileQueryDao: UserPublicProfileQueryDao
) : GetUserPublicProfileUseCase {

    override suspend fun getUserPublicProfile(
        username: String,
        fields: Set<String>
    ): UserPublicProfile {
        val userPublicProfile = userPublicProfileQueryDao.findByUserNameWithFields(username, fields)
        if (userPublicProfile == null) throw UserNotFoundException.byUsername(username)
        return userPublicProfile
    }
}