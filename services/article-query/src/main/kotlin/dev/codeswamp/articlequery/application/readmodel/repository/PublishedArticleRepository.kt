package dev.codeswamp.articlequery.application.readmodel.repository

import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle

interface PublishedArticleRepository {
    suspend fun findByArticleId(articleId: Long): PublishedArticle?
    suspend fun findByFolderIdAndSlug(folderId: Long, slug: String): PublishedArticle?
}