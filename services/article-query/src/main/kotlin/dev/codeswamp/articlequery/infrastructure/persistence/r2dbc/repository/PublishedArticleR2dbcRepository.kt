package dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.entity.PublishedArticleEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PublishedArticleR2dbcRepository : CoroutineCrudRepository<PublishedArticleEntity, Long> {
    suspend fun findByFolderIdAndSlug(folderId: Long, slug: String): PublishedArticleEntity?
}