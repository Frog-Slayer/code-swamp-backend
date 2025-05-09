package dev.codeswamp.core.article.domain.service

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.model.ArticleDiff
import dev.codeswamp.core.article.domain.repository.ArticleDiffRepository
import dev.codeswamp.core.article.domain.support.ArticleDiffCalculator
import org.springframework.stereotype.Service

@Service
class ArticleHistoryService(
    private val articleDiffRepository: ArticleDiffRepository,
    private val diffCalculator: ArticleDiffCalculator
) {
    fun calculateDiff(original: Article?, updated: Article) : ArticleDiff? {
        TODO("Not yet implemented")
    }

    fun save(articleDiff: ArticleDiff): ArticleDiff? {
        return articleDiffRepository.save(articleDiff)
    }

    fun getHistory(articleId: Long) : List<ArticleDiff> {
        TODO("Not yet implemented")
    }

    fun buildFullContentFromHistory(history: List<ArticleDiff>) : String {
        TODO("Not yet implemented")
    }

    fun rollbackTo(articleId: Long, versionId: Long): Article {
        TODO("Not yet implemented")
    }
}