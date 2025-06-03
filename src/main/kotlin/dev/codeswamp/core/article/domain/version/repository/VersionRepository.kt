package dev.codeswamp.core.article.domain.version.repository

import dev.codeswamp.core.article.domain.version.model.Version

interface VersionRepository {
    fun save(version: Version): Version
    fun findById(id: Long) : Version?

    fun findByArticleId(articleId: Long) : List<Version>
    fun countByArticleId(articleId: Long) : Long

    fun findAllByIdsIn(diffs: List<Long>) : List<Version>
    fun deleteByArticleId(articleId: Long)

    fun findLCA(version1Id: Long, version2Id: Long) : Version?
    fun findNearestSnapshotBefore(versionId: Long) : Version
    fun findDiffPathBetween(version1Id: Long, version2Id: Long) : List<Version>
}