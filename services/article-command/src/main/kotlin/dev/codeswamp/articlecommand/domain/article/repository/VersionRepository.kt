package dev.codeswamp.articlecommand.domain.article.repository

import dev.codeswamp.articlecommand.domain.article.model.Version

interface VersionRepository {
    suspend fun insertVersions(versions: List<Version>)
    suspend fun updateVersions(versions: List<Version>)
    suspend fun findAllByArticleId(articleId: Long): List<Version>
    suspend fun findByIdOrNull(id: Long): Version?
    suspend fun deleteAllByArticleIdIn(articleIds: List<Long>)
    suspend fun deleteByArticleId(articleId: Long)
}