package dev.codeswamp.article.infrastructure.persistence.jpa.repository

import dev.codeswamp.article.infrastructure.persistence.jpa.entity.ArticleMetadataEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ArticleMetadataJpaRepository : JpaRepository<ArticleMetadataEntity, Long> {
    @Query("select a.id from ArticleMetadataEntity a where a.folderId = :folderId and a.slug = :slug")
    fun findIdByFolderIdAndSlug(folderId: Long, slug: String): Long?

    fun findAllIdsByFolderIdIn(folderIds: List<Long>): List<Long>
    fun deleteAllByIdIn(ids: List<Long>)
}