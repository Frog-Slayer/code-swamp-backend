package dev.codeswamp.article.application.readmodel.repository

import dev.codeswamp.article.application.readmodel.model.PublishedArticle

interface PublishedArticleRepository {
    fun save(publishedArticle: PublishedArticle): PublishedArticle
    fun findByArticleId(articleId: Long): PublishedArticle?
    fun findByFolderIdAndSlug(folderId: Long, slug: String): PublishedArticle?
}