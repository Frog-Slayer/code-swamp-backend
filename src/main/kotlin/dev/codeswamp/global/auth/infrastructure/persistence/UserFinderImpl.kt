package dev.codeswamp.global.auth.infrastructure.persistence

import dev.codeswamp.core.user.domain.repository.UserRepository
import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.service.UserFinder
import org.springframework.stereotype.Service

@Service
class UserFinderImpl(
    private val userRepository: UserRepository //도메인 간 의존은 인프라를 통해 간접적으로
) : UserFinder {
    override fun findBySubject(subject: String): AuthUser? {
        val user = userRepository.findByEmail(subject) ?: throw IllegalStateException("User not found")//TODO

        return AuthUser(
            id = user.id!!,
            username = user.username.value!!,
            roles = setOf(user.role.name),//TODO
        )
    }

    override fun findById(id: Long): AuthUser? {
                val user = userRepository.findById(id) ?: throw IllegalStateException("User not found")//TODO

        return AuthUser(
            id = user.id!!,
            username = user.username.value!!,
            roles = setOf(user.role.name),//TODO
        )
    }
}