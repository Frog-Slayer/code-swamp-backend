package dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.dto.ArticleSummaryDto
import dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.entity.PublishedArticleEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface PublishedArticleR2dbcRepository : CoroutineCrudRepository<PublishedArticleEntity, Long> {
    suspend fun findByFolderIdAndSlug(folderId: Long, slug: String): PublishedArticleEntity?

    suspend fun existsByIdAndVersionId(id: Long, versionId: Long): Boolean

    @Query("""
        SELECT  
            id,
            version_id,
            author_id,
            folder_id,
            created_at,
            updated_at,
            summary,
            thumbnail,
            is_public,
            slug,
            title
        FROM published_articles
        WHERE (is_public = true OR author_id = :userId)
        AND (created_at, id) < (:createdAt, :articleId)
        ORDER BY created_at ASC, id ASC
        LIMIT :limit
    """)
    suspend fun findArticlesBeforeCursor(
        @Param("userId") userId: Long?,
        @Param("createdAt") createdAt: Instant,
        @Param("articleId") articleId: Long,
        @Param("limit") limit: Int
    ) : List<ArticleSummaryDto>


     @Query("""
        SELECT  
            id,
            version_id,
            author_id,
            folder_id,
            created_at,
            updated_at,
            summary,
            thumbnail,
            is_public,
            slug,
            title
        FROM published_articles
        WHERE id = :articleId
    """)
    suspend fun findArticleSummaryById(
         @Param("articleId") id: Long
    ): ArticleSummaryDto?
}