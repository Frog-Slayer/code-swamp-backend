package dev.codeswamp.domain.article.domain.service

import dev.codeswamp.domain.article.domain.model.Article
import dev.codeswamp.domain.article.domain.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService (
    private val articleRepository: ArticleRepository
){

    fun create(article: Article): Article {
        return articleRepository.save(article)
    }

    fun update(article: Article): Article {
        return articleRepository.save(article)
    }

    fun delete(article: Article) {
        articleRepository.delete(article)
    }

    fun findAllByIds(articleIds : List<Long>): List<Article> {
        return articleRepository.findAllByIds(articleIds)
    }

    fun findById(articleId: Long): Article {
        return articleRepository.findById(articleId)
    }

    fun publish(article: Article) {
        article.publish()
        articleRepository.save(article)
    }
}