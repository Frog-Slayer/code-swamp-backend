package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.ArticleMetadataEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface ArticleMetadataR2dbcRepository : CoroutineCrudRepository<ArticleMetadataEntity, Long> {

    @Query("""
        INSERT INTO article_metadata (id, folder_id, slug, author_id, is_public, is_published, created_at, summary, thumbnail)
        VALUES (:id, :folderId, :slug, :authorId, :isPublic, :isPublished, :createdAt, :summary, :thumbnail)
        RETURNING *
    """)
    suspend fun insert(
        @Param("id") id: Long,
        @Param("folderId") folderId: Long,
        @Param("slug") slug: String?,
        @Param("authorId") authorId: Long,
        @Param("isPublic") isPublic: Boolean,
        @Param("isPublished") isPublished: Boolean,
        @Param("createdAt") createdAt: Instant,
        @Param("summary") summary: String,
        @Param("thumbnail") thumbnail: String?
    ) : ArticleMetadataEntity

    @Query("select a.id from article_metadata a where a.folder_id = :folderId and a.slug = :slug")
    suspend fun findIdByFolderIdAndSlug(folderId: Long, slug: String): Long?
    suspend fun findAllIdsByFolderIdIn(folderIds: List<Long>): List<Long>
    suspend fun deleteAllByIdIn(ids: List<Long>)
}