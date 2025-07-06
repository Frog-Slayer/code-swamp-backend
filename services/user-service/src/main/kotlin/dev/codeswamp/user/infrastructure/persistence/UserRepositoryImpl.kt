package dev.codeswamp.user.infrastructure.persistence

import dev.codeswamp.user.domain.user.model.Nickname
import dev.codeswamp.user.domain.user.model.User
import dev.codeswamp.user.domain.user.model.Username
import dev.codeswamp.user.domain.user.repository.UserRepository
import dev.codeswamp.user.infrastructure.persistence.entity.UserEntity
import dev.codeswamp.user.infrastructure.persistence.repository.UserR2dbcRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userR2dbcRepository: UserR2dbcRepository,
) : UserRepository {
    override suspend fun insert(user: User): User {
        return userR2dbcRepository.insert(
            user.id,
            user.username.value,
            user.nickname.value,
            user.profileImage
        ).toDomain()
    }

    override suspend fun save(user: User): User {
        return userR2dbcRepository.save(UserEntity.Companion.from(user)).toDomain()
    }

    override suspend fun findById(id: Long): User? {
        return userR2dbcRepository.findById(id)?.toDomain()
    }

    override suspend fun findAllByIdsIn(ids: List<Long>): List<User> {
        return userR2dbcRepository.findAllById(ids).map { it.toDomain() }.toList()
    }
}