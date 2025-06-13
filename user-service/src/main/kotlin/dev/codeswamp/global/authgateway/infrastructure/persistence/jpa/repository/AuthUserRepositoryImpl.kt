package dev.codeswamp.global.authgateway.infrastructure.persistence.jpa.repository

import dev.codeswamp.global.authgateway.domain.model.AuthUser
import dev.codeswamp.global.authgateway.domain.repository.AuthUserRepository
import dev.codeswamp.global.authgateway.infrastructure.persistence.jpa.entity.AuthUserEntity
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