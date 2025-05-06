package dev.codeswamp.core.user.domain.service

import dev.codeswamp.core.user.domain.model.Nickname
import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.domain.model.Username
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

    override fun findUserByUsername(username: Username): User {
        TODO("Not yet implemented")
    }

    override fun findUserByNickname(nickname: Nickname): User {
        TODO("Not yet implemented")
    }
}