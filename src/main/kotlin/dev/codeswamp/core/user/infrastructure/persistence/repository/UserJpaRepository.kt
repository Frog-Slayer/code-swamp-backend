package dev.codeswamp.core.user.infrastructure.persistence.repository

import dev.codeswamp.core.user.infrastructure.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): Optional<UserEntity>
    fun findByNickname(nickname: String): Optional<UserEntity>
    fun findByEmail(email: String): Optional<UserEntity>
}

