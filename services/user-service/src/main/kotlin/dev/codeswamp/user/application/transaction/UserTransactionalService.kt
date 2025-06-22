package dev.codeswamp.user.application.transaction

import dev.codeswamp.user.domain.user.model.User
import dev.codeswamp.user.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserTransactionalService(
    private val userRepository: UserRepository
) {
    @Transactional
    fun create(userId: Long, username: String, nickname: String, profileImage: String?): User {
        val user = User.of(userId, username, nickname, profileImage)
        return userRepository.save(user).registered()
    }

    @Transactional
    fun save(user: User): User {
        return userRepository.save(user)
    }
}