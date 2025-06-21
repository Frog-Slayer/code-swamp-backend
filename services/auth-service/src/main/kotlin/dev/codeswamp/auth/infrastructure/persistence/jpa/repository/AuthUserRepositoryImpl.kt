package dev.codeswamp.auth.infrastructure.persistence.jpa.repository

import dev.codeswamp.auth.domain.model.AuthUser
import dev.codeswamp.auth.domain.repository.AuthUserRepository
import dev.codeswamp.auth.infrastructure.persistence.jpa.entity.AuthUserEntity
import org.springframework.stereotype.Repository

@Repository
class AuthUserRepositoryImpl(
    private val authUserJpaRepository: AuthUserJpaRepository
) : AuthUserRepository {
    override suspend fun save(authUser: AuthUser): AuthUser {
        return authUserJpaRepository.save(AuthUserEntity.Companion.from(authUser)).toDomain()
    }

    override suspend fun findByUsername(username: String): AuthUser? {
        return authUserJpaRepository.findByUsername(username)?.toDomain()
    }

    override suspend fun findById(id: Long): AuthUser? {
        val authUserEntity: AuthUserEntity? = authUserJpaRepository.findById(id).orElse(null)
        return authUserEntity?.toDomain()
    }
}