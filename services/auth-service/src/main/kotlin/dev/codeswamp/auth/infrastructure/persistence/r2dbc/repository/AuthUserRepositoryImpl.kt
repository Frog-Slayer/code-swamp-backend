package dev.codeswamp.auth.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.auth.domain.model.AuthUser
import dev.codeswamp.auth.domain.repository.AuthUserRepository
import dev.codeswamp.auth.infrastructure.persistence.r2dbc.entity.AuthUserEntity
import org.springframework.stereotype.Repository

@Repository
class AuthUserRepositoryImpl(
    private val authUserR2dbcRepository: AuthUserR2dbcRepository
) : AuthUserRepository {
    //TODO
    override suspend fun save(authUser: AuthUser): AuthUser {
        val entity = AuthUserEntity.from(authUser)
        return authUserR2dbcRepository.upsert(entity.id, entity.email, entity.role).toDomain()
    }

    override suspend fun findByEmail(email: String): AuthUser? {
        return authUserR2dbcRepository.findByEmail(email)?.toDomain()
    }

    override suspend fun findById(id: Long): AuthUser? {
        val authUserEntity = authUserR2dbcRepository.findById(id)
        return authUserEntity?.toDomain()
    }
}