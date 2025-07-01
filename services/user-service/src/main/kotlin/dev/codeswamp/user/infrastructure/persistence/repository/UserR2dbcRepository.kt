package dev.codeswamp.user.infrastructure.persistence.repository

import dev.codeswamp.user.infrastructure.persistence.entity.UserEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserR2dbcRepository : CoroutineCrudRepository<UserEntity, Long> {

    @Query ("""
        INSERT INTO users (id, username, nickname, profile_image) 
        VALUES (:id, :username, :nickname, :profileImage)
        RETURNING *
    """)
    suspend fun insert(
        @Param("id") id: Long,
        @Param("username") username: String,
        @Param("nickname") nickname: String,
        @Param("profileImage") profileImage: String?
    ) : UserEntity

    suspend fun findByUsername(username: String): UserEntity?
    suspend fun findByNickname(nickname: String): UserEntity?
}

