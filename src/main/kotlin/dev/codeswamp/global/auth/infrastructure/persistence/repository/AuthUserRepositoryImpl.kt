package dev.codeswamp.global.auth.infrastructure.persistence.repository

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.repository.AuthUserRepository
import dev.codeswamp.global.auth.infrastructure.persistence.entity.AuthUserEntity
import org.springframework.stereotype.Repository

@Repository
class AuthUserRepositoryImpl(
    private val authUserJpaRepository: AuthUserJpaRepository
): AuthUserRepository {
    override fun save(authUser: AuthUser): AuthUser {
        return authUserJpaRepository.save(AuthUserEntity.from(authUser)).toDomain()
    }

    override fun findByUsername(username: String): AuthUser? {
        return authUserJpaRepository.findByUsername(username)?.toDomain()
    }

    override fun findById(id: Long): AuthUser? {
        val authUserEntity: AuthUserEntity? = authUserJpaRepository.findById(id).orElse(null)
        return authUserEntity?.toDomain()
    }
}