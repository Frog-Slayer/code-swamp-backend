package dev.codeswamp.global.auth.infrastructure.persistence.jpa.repository

import dev.codeswamp.global.auth.infrastructure.persistence.jpa.entity.AuthUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthUserJpaRepository : JpaRepository<AuthUserEntity, Long> {
    fun findByUsername(username: String): AuthUserEntity?
}