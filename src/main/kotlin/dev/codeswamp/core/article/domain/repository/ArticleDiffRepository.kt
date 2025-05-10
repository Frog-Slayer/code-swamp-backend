package dev.codeswamp.core.article.domain.repository

import dev.codeswamp.core.article.domain.model.ArticleDiff

interface ArticleDiffRepository {
    fun save(articleDiff: ArticleDiff): ArticleDiff
    fun findById(id: Long) : ArticleDiff?

    fun findByArticleId(articleId: Long) : List<ArticleDiff>
    fun countByArticleId(articleId: Long) : Long

    fun findAllByIdsIn(diffs: List<Long>) : List<ArticleDiff>
    fun deleteByArticleId(articleId: Long)
}