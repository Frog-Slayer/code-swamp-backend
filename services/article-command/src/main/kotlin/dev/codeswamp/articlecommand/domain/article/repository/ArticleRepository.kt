package dev.codeswamp.articlecommand.domain.article.repository

import dev.codeswamp.articlecommand.domain.article.model.Article

interface ArticleRepository {

    suspend fun create(article: Article) : Article
    suspend fun update(article: Article): Article

    suspend fun findIdByFolderIdAndSlug(folderId: Long, slug: String): Long?
    suspend fun findByIdAndVersionId(articleId: Long, versionId: Long): Article?

    suspend fun countVersionsOfArticle(articleId: Long): Long

    suspend fun deleteAllByFolderIdIn(folderIds: List<Long>)
    suspend fun deleteById(id: Long)
}