package dev.codeswamp.auth.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.auth.infrastructure.persistence.r2dbc.entity.AuthUserEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AuthUserR2dbcRepository : CoroutineCrudRepository<AuthUserEntity, Long> {

    @Query("""
        INSERT INTO auth_user( id, email, role) 
        VALUES (:id, :email, :role)
        ON CONFLICT (id) 
        DO UPDATE SET 
            email = :email,
            role = :role
        RETURNING *
    """)
    suspend fun upsert(
        @Param("id") id: Long,
        @Param("email") email: String,
        @Param("role") role: String
    ): AuthUserEntity

    suspend fun findByEmail(email: String): AuthUserEntity?
}