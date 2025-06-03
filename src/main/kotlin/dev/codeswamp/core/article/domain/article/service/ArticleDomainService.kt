package dev.codeswamp.core.article.domain.article.service

import dev.codeswamp.core.article.domain.article.model.Article
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.domain.version.service.VersionService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleDomainService (
    private val articleRepository: ArticleRepository,
    private val versionService: VersionService
){

    @Transactional
    fun create(article: Article): Article {
        val saved = articleRepository.save(article)

        val calculated = versionService.calculateDiff(null,saved)
                ?:throw Exception("something went wrong") //TODO

        val diff = versionService.save(calculated)

        saved.currentVersion = diff.id ?: throw Exception("something went wrong")//TODO

        return articleRepository.save(saved)
    }

    @Transactional
    fun update(article: Article): Article {
        val original = articleRepository.findById(article.id!!) ?: throw IllegalArgumentException("Article does not exist")

        val diff = versionService.calculateDiff(original, article)//TODO -> full content를 받는 게 아니라 diff를 받음

        if (diff != null) {
            val savedDiff = versionService.save(diff)
            article.currentVersion = savedDiff.id!!
        }

        return articleRepository.save(article)
    }

    fun deleteById(articleId: Long) {
        versionService.deleteArticle(articleId)
        articleRepository.deleteById(articleId)
    }

    @Transactional
    fun rollbackTo(article: Article, rollbackVersionId: Long): Article {
        val rollbackedArticle = versionService.getRollbackedArticle(article, rollbackVersionId)
        return articleRepository.save(rollbackedArticle)
    }
}