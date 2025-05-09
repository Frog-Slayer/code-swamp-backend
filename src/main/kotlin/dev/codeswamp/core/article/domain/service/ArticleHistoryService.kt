package dev.codeswamp.core.article.domain.service

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.model.ArticleDiff
import org.springframework.stereotype.Service

@Service
class ArticleHistoryService {
    fun saveDiff(original: Article?, updated: Article) : ArticleDiff? {
        TODO("Not yet implemented")
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