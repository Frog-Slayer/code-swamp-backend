package dev.codeswamp.core.article.domain.article.service

import dev.codeswamp.core.article.domain.article.model.Article
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.domain.article.service.ArticleHistoryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleDomainService (
    private val articleRepository: ArticleRepository,
    private val articleHistoryService: ArticleHistoryService
){

    @Transactional
    fun create(article: Article): Article {
        val saved = articleRepository.save(article)

        val calculated = articleHistoryService.calculateDiff(null,saved)
                ?:throw Exception("something went wrong") //TODO

        val diff = articleHistoryService.save(calculated)

        saved.currentVersion = diff.id ?: throw Exception("something went wrong")//TODO

        return articleRepository.save(saved)
    }

    @Transactional
    fun update(article: Article): Article {
        val original = articleRepository.findById(article.id!!) ?: throw IllegalArgumentException("Article does not exist")

        val diff = articleHistoryService.calculateDiff(original, article)//TODO -> full content를 받는 게 아니라 diff를 받음

        if (diff != null) {
            val savedDiff = articleHistoryService.save(diff)
            article.currentVersion = savedDiff.id!!
        }

        return articleRepository.save(article)
    }

    fun deleteById(articleId: Long) {
        articleHistoryService.deleteArticle(articleId)
        articleRepository.deleteById(articleId)
    }

    @Transactional
    fun rollbackTo(article: Article, rollbackVersionId: Long): Article {
        val rollbackedArticle = articleHistoryService.getRollbackedArticle(article, rollbackVersionId)
        return articleRepository.save(rollbackedArticle)
    }
}