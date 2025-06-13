package dev.codeswamp.user.domain.service

import dev.codeswamp.user.domain.model.Nickname
import dev.codeswamp.user.domain.model.User
import dev.codeswamp.user.domain.model.Username
import dev.codeswamp.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override fun save(user: User) : User {
        return userRepository.save(user)
    }

    override fun findById(id: Long): User? {
        return userRepository.findById(id)
    }

    override fun findUserByUsername(username: Username): User? {
        return userRepository.findByUsername(username)
    }

    override fun findUserByNickname(nickname: Nickname): User? {
        return userRepository.findByNickname(nickname)
    }
}