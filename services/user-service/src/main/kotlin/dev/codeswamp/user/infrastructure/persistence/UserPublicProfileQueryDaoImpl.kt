package dev.codeswamp.user.infrastructure.persistence

import dev.codeswamp.user.application.usecase.profile.UserPublicProfile
import dev.codeswamp.user.application.usecase.profile.UserPublicProfileQueryDao
import dev.codeswamp.user.infrastructure.persistence.repository.UserR2dbcRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.stereotype.Repository

@Repository
class UserPublicProfileQueryDaoImpl(
    private val databaseClient: DatabaseClient,
): UserPublicProfileQueryDao {

    private val columnMappings = mapOf(
        "userId" to "id",
        "username" to "username",
        "nickname" to "nickname",
        "profileImage" to "profile_image"
    )

    override suspend fun findByUserNameWithFields(
        username: String,
        fields: Set<String>
    ): UserPublicProfile? {
        val selectedColumns = fields.mapNotNull {  columnMappings[it] }.toSet()

        if (selectedColumns.isEmpty()) return null

        val selectClause = selectedColumns.joinToString(", ")

        val sql = """
            SELECT $selectClause
            FROM users
            WHERE username = :username
        """.trimIndent()

        return databaseClient.sql(sql)
            .bind("username", username)
            .map { row, _ ->
                UserPublicProfile (
                    userId = if ("id" in selectedColumns) row.get("id", java.lang.Long::class.java)?.toLong() ?: 0 else 0,
                    username = if ("username" in selectedColumns) row.get("username", String::class.java) ?: "" else "",
                    nickname = if ("nickname" in selectedColumns) row.get("nickname", String::class.java) ?: "" else "",
                    profileImage = if ("profile_image" in selectedColumns) row.get("profile_image", String::class.java) else null,
                )
            }
            .awaitOneOrNull()
    }
}