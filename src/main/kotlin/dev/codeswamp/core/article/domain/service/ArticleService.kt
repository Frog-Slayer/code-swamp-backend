package dev.codeswamp.core.article.domain.service

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.repository.ArticleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleService (
    private val articleRepository: ArticleRepository,
    private val articleHistoryService: ArticleHistoryService
){

    fun create(article: Article): Article {
        val saved = articleRepository.save(article)

        val calculated = articleHistoryService.calculateDiff(null,saved)
                ?:throw Exception("something went wrong") //TODO

        val diff = articleHistoryService.save(calculated)

        saved.currentVersion = diff.id ?: throw Exception("something went wrong")//TODO

        return articleRepository.save(saved)
    }

    fun update(article: Article): Article {
        val original = articleRepository.findById(article.id!!) ?: throw IllegalArgumentException("Article does not exist")

        val diff = articleHistoryService.calculateDiff(original, article)
        if (diff != null) {
            val savedDiff = articleHistoryService.save(diff)
            article.currentVersion = savedDiff.id!!
        }

        return articleRepository.save(article)
    }

    fun findById(articleId: Long): Article? {
        return articleRepository.findById(articleId)
    }

    fun findAllByIds(articleIds: List<Long>): List<Article> {
        return articleRepository.findAllByIds(articleIds)
    }

    fun deleteById(articleId: Long) {
        articleHistoryService.deleteArticle(articleId)
        articleRepository.deleteById(articleId)
    }
}