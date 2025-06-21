package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.ArticleMetadataEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleMetadataJpaRepository : CoroutineCrudRepository<ArticleMetadataEntity, Long> {
    @Query("select a.id from article_metadata a where a.folder_id = :folderId and a.slug = :slug")
    suspend fun findIdByFolderIdAndSlug(folderId: Long, slug: String): Long?
    suspend fun findAllIdsByFolderIdIn(folderIds: List<Long>): List<Long>
    suspend fun deleteAllByIdIn(ids: List<Long>)
}