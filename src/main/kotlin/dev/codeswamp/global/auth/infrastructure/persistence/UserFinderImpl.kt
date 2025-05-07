package dev.codeswamp.global.auth.infrastructure.persistence

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.service.UserFinder
import org.springframework.stereotype.Service

@Service
class UserFinderImpl : UserFinder {
    override fun findBySubject(subject: String): AuthUser? {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): AuthUser? {
        TODO("Not yet implemented")
    }
}