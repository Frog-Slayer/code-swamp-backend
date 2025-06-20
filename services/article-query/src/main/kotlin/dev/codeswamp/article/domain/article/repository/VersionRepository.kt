package dev.codeswamp.article.domain.article.repository

import dev.codeswamp.article.domain.article.model.Version

interface VersionRepository {
    fun save(version: Version): Version

    fun findByIdOrNull(id: Long) : Version?

    fun deleteAllByArticleIdIn(articleIds: List<Long>)
    fun deleteByArticleId(articleId: Long)

    fun findPreviousPublishedVersion(articleId: Long, versionId: Long) : Version?

    fun findNearestBaseTo(versionId : Long) : Version?

    fun findDiffChainBetween(baseId: Long, targetId: Long) : List<String>
}