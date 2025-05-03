package dev.codeswamp.domain.article.infrastructure.persistence.repository

import dev.codeswamp.domain.article.domain.model.Article
import dev.codeswamp.domain.article.domain.repository.ArticleRepository

class ArticleRepositoryImpl (
    val articleMetadataJpaRepository: ArticleMetadataJpaRepository,
    val articleContentJpaRepository: ArticleContentJpaRepository,
) : ArticleRepository {
    override fun save(article: Article): Article {
        TODO("Not yet implemented")
    }

    override fun delete(article: Article) {
        TODO("Not yet implemented")
    }

    override fun findAllByIds(articleIds: List<Long>): List<Article> {
        TODO("Not yet implemented")
    }

    override fun findById(articleId: Long): Article {
        TODO("Not yet implemented")
    }
}