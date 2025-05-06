package dev.codeswamp.core.user.domain.service

import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.domain.repository.UserRepository
import dev.codeswamp.core.user.infrastructure.entity.UserEntity
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override fun save(user: User) {
        userRepository.save(user)
    }

    override fun findById(id: Long): User {
        TODO("Not yet implemented")
    }

    override fun findUserByEmail(email: String): User {
        TODO("Not yet implemented")
    }

    override fun findUserByUsername(username: String): User {
        TODO("Not yet implemented")
    }

    override fun findUserByNickname(nickname: String): User {
        TODO("Not yet implemented")
    }

    override fun isValidUsername(username: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isValidNickname(nickname: String): Boolean {
        TODO("Not yet implemented")
    }
}