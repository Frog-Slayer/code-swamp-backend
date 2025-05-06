package dev.codeswamp.core.user.infrastructure.persistence.repository

import dev.codeswamp.core.user.domain.model.Nickname
import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.domain.model.Username
import dev.codeswamp.core.user.domain.repository.UserRepository
import dev.codeswamp.core.user.infrastructure.persistence.entity.UserEntity
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun save(user: User): User {
        return userJpaRepository.save(UserEntity.from(user)).toDomain()
    }

    override fun findById(id: Long): Optional<User> {
        return userJpaRepository.findById(id).map { it.toDomain() }
    }

    override fun findByUsername(username: Username): Optional<User> {
        return userJpaRepository.findByUsername(username.username).map { it.toDomain() }
    }

    override fun findByEmail(email: String): Optional<User> {
        return userJpaRepository.findByEmail(email).map { it.toDomain() }
    }

    override fun findByNickname(nickname: Nickname): Optional<User> {
        return userJpaRepository.findByNickname(nickname.nickname ).map { it.toDomain() }
    }
}