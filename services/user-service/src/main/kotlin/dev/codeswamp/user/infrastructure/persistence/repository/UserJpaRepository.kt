package dev.codeswamp.user.infrastructure.persistence.repository

import dev.codeswamp.user.infrastructure.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
    fun findByNickname(nickname: String): UserEntity?
}

