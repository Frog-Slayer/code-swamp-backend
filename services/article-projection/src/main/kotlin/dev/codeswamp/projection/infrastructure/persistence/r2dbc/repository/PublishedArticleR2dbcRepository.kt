package dev.codeswamp.projection.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.projection.application.readmodel.model.PublishedArticle
import dev.codeswamp.projection.infrastructure.persistence.r2dbc.entity.PublishedArticleEntity
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import java.time.Instant

interface PublishedArticleR2dbcRepository : CoroutineCrudRepository<PublishedArticleEntity, Long> {

    @Modifying
    @Query("""
        INSERT INTO published_article (id, author_id, folder_id, created_at, updated_at, summary, thumbnail, is_public, slug, title, content)
        VALUES (:id, :author_id, :folder_id, :created_at, :updated_at, :summary, :thumbnail, :is_public, :slug, :title, :content)
        ON CONFLICT (id)
        DO UPDATE SET
            updated_at = :updatedAt,
            summary = :summary,
            thumbnail = :thumbnail,
            is_public = :is_public,
            slug = :slug,
            title = :title,
            content = :content
        RETURNING * 
    """)
    suspend fun upsert(
        @Param("id") id: Long,
        @Param("author_id") authorId: Long,
        @Param("folder_id") folderId: Long,

        @Param("created_at") createdAt: Instant,
        @Param("updated_at") updatedAt: Instant,

        @Param("summary") summary: String,
        @Param("thumbnail") thumbnail: String? = null,
        @Param("is_public") isPublic: Boolean,

        @Param("slug") slug: String,
        @Param("title") title: String,
        @Param("content") content: String,
    ) : PublishedArticleEntity

    override suspend fun findById(id: Long) : PublishedArticleEntity?
}