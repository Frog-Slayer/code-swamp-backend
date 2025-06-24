package dev.codeswamp.articlecommand.domain.article.repository

import dev.codeswamp.articlecommand.domain.article.model.Version

interface VersionRepository {
    suspend fun save(version: Version): Version

    suspend fun findByIdOrNull(id: Long): Version?

    suspend fun deleteAllByArticleIdIn(articleIds: List<Long>)
    suspend fun deleteByArticleId(articleId: Long)

    suspend fun findPublishedVersionByArticleId(articleId: Long): Version?

    suspend fun findNearestBaseTo(versionId: Long): Version?

    suspend fun findDiffChainBetween(baseId: Long, targetId: Long): List<String>
}