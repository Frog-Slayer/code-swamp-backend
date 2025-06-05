package dev.codeswamp.core.article.domain.article.repository

import dev.codeswamp.core.article.domain.article.model.Version

interface VersionRepository {
    fun findByIdOrNull(id: Long) : Version?

    fun countByArticleId(articleId: Long) : Long
    fun deleteByArticleId(articleId: Long)

    fun findPublishedVersionByArticleId(articleId: Long) : Version?
    fun findDiffChainFromNearestSnapshot(versionId: Long) : List<String>
}