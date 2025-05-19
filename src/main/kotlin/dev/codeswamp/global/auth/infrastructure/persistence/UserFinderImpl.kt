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
        val user = userRepository.findByEmail(subject)

        return user?.let {
            AuthUser(
                id = it.id!!,
                username = it.email,
                roles = setOf(it.role.name),//TODO
            )
        }
    }

    override fun findById(id: Long): AuthUser? {
        val user = userRepository.findById(id)

        return user?.let {
            AuthUser(
                id = it.id!!,
                username = it.email,
                roles = setOf(it.role.name),//TODO
            )
        }
    }

    override fun findByEmail(email: String): AuthUser? {
        val user = userRepository.findByEmail(email)

        return user?.let {
            AuthUser(
                id = it.id!!,
                username = it.email,
                roles = setOf(it.role.name),//TODO
            )
        }
    }
}