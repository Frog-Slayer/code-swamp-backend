package dev.codeswamp.articlecommand.domain.article.repository

import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle

interface ArticleRepository {

    suspend fun create(versionedArticle: VersionedArticle) : VersionedArticle
    suspend fun update(versionedArticle: VersionedArticle): VersionedArticle

    suspend fun findIdByFolderIdAndSlug(folderId: Long, slug: String): Long?
    suspend fun findByIdAndVersionId(articleId: Long, versionId: Long): VersionedArticle?

    suspend fun countVersionsOfArticle(articleId: Long): Long

    suspend fun deleteAllByFolderIdIn(folderIds: List<Long>)
    suspend fun deleteById(id: Long)
}