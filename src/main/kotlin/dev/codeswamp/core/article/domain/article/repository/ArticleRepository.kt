package dev.codeswamp.core.article.domain.article.repository

import dev.codeswamp.core.article.domain.article.model.Version
import dev.codeswamp.core.article.domain.article.model.VersionedArticle

interface ArticleRepository {
    fun save(versionedArticle: VersionedArticle): VersionedArticle
    fun findIdByFolderIdAndSlug(folderId: Long, slug: String): Long?
    fun findByIdAndVersionId(articleId: Long, versionId: Long): VersionedArticle?

    fun findPreviousPublishedVersion(articleId: Long, versionId: Long): Version?
    fun findVersionByVersionId(versionId: Long): Version?

    fun countVersionsOfArticle(articleId: Long): Long
    fun saveVersion(version: Version): Version;

    fun deleteAllByFolderIdIn(folderIds: List<Long>)
    fun deleteById(id: Long)
}