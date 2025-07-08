package dev.codeswamp.articlequery.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle
import dev.codeswamp.articlequery.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.entity.PublishedArticleEntity
import dev.codeswamp.databasequery.QuerySpecBuilder
import dev.codeswamp.databasequery.executeQueryList
import dev.codeswamp.databasequery.executeQuerySingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.temporal.ChronoUnit

@Repository
class PublishedArticleRepositoryImpl(
    private val databaseClient: DatabaseClient
) : PublishedArticleRepository {
    private val tableName = "published_articles"
    private fun isReadable(userId: Long?) = if (userId != null) "is_public = true OR author_id = :userId" else "is_public = true"

    override suspend fun findByArticleId(
        userId: Long?,
        articleId: Long,
        fields: Set<String>
    ): PublishedArticle? {
        val querySpec  = QuerySpecBuilder(tableName)
            .select(*fields.toTypedArray())
            .where("id = :articleId")
            .where ( isReadable(userId))
            .bind("articleId", articleId)
            .apply { if ( userId != null ) bind("userId", userId) }
            .build()

        return databaseClient.executeQuerySingle<PublishedArticleEntity, PublishedArticle>(querySpec) {
            it.toDomain()
        }
    }

    override suspend fun findAllByAuthorId(
        userId: Long?,
        authorId: Long,
        fields: Set<String>
    ): List<PublishedArticle> {
        val querySpec  = QuerySpecBuilder(tableName)
            .select(*fields.toTypedArray())
            .where("author_id = :authorId")
            .where( isReadable(userId))
            .bind("authorId", authorId)
            .apply { if ( userId != null ) bind("userId", userId) }
            .build()

        return databaseClient.executeQueryList<PublishedArticleEntity, PublishedArticle>(querySpec) {
            it.toDomain()
        }
    }

    override suspend fun findByFolderIdAndSlug(
        userId: Long?,
        folderId: Long,
        slug: String,
        fields: Set<String>
    ): PublishedArticle? {
        val querySpec  = QuerySpecBuilder(tableName)
            .select(*fields.toTypedArray())
            .where("folder_id= :folderId", "slug = :slug")
            .where( isReadable(userId))
            .bind("folderId", folderId)
            .bind("slug", slug)
            .apply { if ( userId != null ) bind("userId", userId) }
            .build()

        return databaseClient.executeQuerySingle<PublishedArticleEntity, PublishedArticle>(querySpec) {
            it.toDomain()
        }
    }

    override suspend fun findRecentArticles(
        userId: Long?,
        lastCreatedAt: Instant?,
        lastArticleId: Long?,
        limit: Int,
        fields: Set<String>
    ): List<PublishedArticle> {
        val createdAt = lastCreatedAt ?: Instant.now().plus(1, ChronoUnit.DAYS)
        val articleId = lastArticleId?:0

        val querySpec  = QuerySpecBuilder(tableName)
            .select(*fields.toTypedArray())
            .where("(created_at, id) < (:createdAt, :articleId)")
            .where( isReadable(userId))
            .orderBy("created_at DESC", "id DESC")
            .limit(limit)
            .bind("createdAt", createdAt)
            .bind("articleId", articleId)
            .bind("limit", limit)
            .apply { if ( userId != null ) bind("userId", userId) }
            .build()

        return databaseClient.executeQueryList<PublishedArticleEntity, PublishedArticle>(querySpec){
            it.toDomain()
        }
    }
}