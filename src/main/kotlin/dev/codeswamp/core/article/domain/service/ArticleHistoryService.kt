package dev.codeswamp.core.article.domain.service

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.model.ArticleDiff
import dev.codeswamp.core.article.domain.repository.ArticleDiffRepository
import dev.codeswamp.core.article.domain.support.ArticleDiffProcessor
import org.springframework.stereotype.Service

@Service
class ArticleHistoryService(
    private val articleDiffRepository: ArticleDiffRepository,
    private val diffCalculator: ArticleDiffProcessor
) {
    fun calculateDiff(original: Article?, updated: Article) : ArticleDiff? {
        val originalContent = original?.content ?: ""

        val diff = diffCalculator.calculateDiff(originalContent, updated.content)

        return diff?.let {
            ArticleDiff(
                diffData = it,
                article = updated,
                createdAt = updated.updatedAt,
            )
        }
    }

    fun save(articleDiff: ArticleDiff): ArticleDiff? {
        return articleDiffRepository.save(articleDiff)
    }

    fun getHistory(articleId: Long) : List<ArticleDiff> {
        return articleDiffRepository.findByArticleId(articleId)
    }

    fun buildFullContentFromHistory(history: List<ArticleDiff>) : String {
        val sortedHistoryDiffList = history.sortedBy {  it.createdAt }.map{ it.diffData }
        return diffCalculator.buildFullContentFromHistory( sortedHistoryDiffList)
    }

    fun rollbackTo(articleId: Long, versionId: Long): Article {
        TODO("Not yet implemented")
    }
}