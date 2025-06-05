package dev.codeswamp.core.article.domain.article.repository

import dev.codeswamp.core.article.domain.article.model.Version

interface VersionRepository {
    fun save(version: Version): Version
    fun findById(id: Long) : Version?

    fun countByArticleId(articleId: Long) : Long
    fun findAllByIdsIn(diffs: List<Long>) : List<Version>
    fun deleteByArticleId(articleId: Long)

    fun findPublishedVersionByArticleId(articleId: Long) : Version
    fun findNearestSnapshotBefore(versionId: Long) : Version//nearest snapshot까지의 path 조회로 하나로 줄일 수 있음
    fun findDiffPathBetween(version1Id: Long, version2Id: Long) : List<Version>

}