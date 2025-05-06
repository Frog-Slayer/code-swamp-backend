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
        return userRepository.findById(id).orElseThrow()
    }

    override fun findUserByEmail(email: String): User {
        return userRepository.findByEmail(email).orElseThrow()
    }

    override fun findUserByUsername(username: Username): User {
        return userRepository.findByUsername(username).orElseThrow()
    }

    override fun findUserByNickname(nickname: Nickname): User {
        return userRepository.findByNickname(nickname).orElseThrow()
    }
}