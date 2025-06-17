package dev.codeswamp.user.infrastructure.persistence.repository

import dev.codeswamp.user.domain.model.Nickname
import dev.codeswamp.user.domain.model.User
import dev.codeswamp.user.domain.model.Username
import dev.codeswamp.user.domain.repository.UserRepository
import dev.codeswamp.user.infrastructure.persistence.entity.UserEntity
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun save(user: User): User {
        return userJpaRepository.save(UserEntity.Companion.from(user)).toDomain()
    }

    override fun findById(id: Long): User? {
        val userEntity : UserEntity? = userJpaRepository.findById(id).orElse(null)
        return  userEntity?.toDomain()
    }

    override fun findByUsername(username: Username): User? {
        return username.value?.let { userJpaRepository.findByUsername(it)?.toDomain()}
    }

    override fun findByNickname(nickname: Nickname): User? {
        return nickname.value?.let { userJpaRepository.findByNickname(it)?.toDomain()}
    }
}