package dev.codeswamp.core.article.domain.article.repository

import dev.codeswamp.core.article.domain.article.model.Article

interface ArticleRepository {
    fun save(article: Article): Article
    fun deleteById(id: Long)

    fun findAllByIds(articleIds : List<Long>): List<Article>
    fun findById(articleId : Long): Article?
    fun findByFolderIdAndSlug(folderId: Long, slug: String): Article?
}