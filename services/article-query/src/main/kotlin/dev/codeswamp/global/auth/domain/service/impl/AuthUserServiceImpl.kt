package dev.codeswamp.global.auth.domain.service.impl

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.token.Subject
import dev.codeswamp.global.auth.domain.model.token.SubjectType
import dev.codeswamp.global.auth.domain.repository.AuthUserRepository
import dev.codeswamp.global.auth.domain.service.AuthUserService
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
class AuthUserServiceImpl(
    private val authUserRepository: AuthUserRepository
): AuthUserService {

    override fun findByUsername(username: String): AuthUser? {
        return authUserRepository.findByUsername(username)
    }

    override fun findBySubject(subject: Subject): AuthUser? {
        return when (subject.type) {
            SubjectType.USERNAME -> findByUsername(subject.value)
        }
    }

    override fun save(authUser: AuthUser) : AuthUser{
        return authUserRepository.save(authUser)
    }

    override fun findById(id: Long): AuthUser? {
        return authUserRepository.findById(id)
    }
}