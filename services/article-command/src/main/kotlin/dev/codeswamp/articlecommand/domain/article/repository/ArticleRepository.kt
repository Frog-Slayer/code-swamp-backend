package dev.codeswamp.articlecommand.domain.article.repository

import dev.codeswamp.articlecommand.domain.article.model.Version
import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle

interface ArticleRepository {
    suspend fun save(versionedArticle: VersionedArticle): VersionedArticle
    suspend fun findIdByFolderIdAndSlug(folderId: Long, slug: String): Long?
    suspend fun findByIdAndVersionId(articleId: Long, versionId: Long): VersionedArticle?

    suspend fun findPreviousPublishedVersion(articleId: Long, versionId: Long): Version?
    suspend fun findVersionByVersionId(versionId: Long): Version?

    suspend fun countVersionsOfArticle(articleId: Long): Long
    suspend fun saveVersion(version: Version): Version;

    suspend fun deleteAllByFolderIdIn(folderIds: List<Long>)
    suspend fun deleteById(id: Long)
}