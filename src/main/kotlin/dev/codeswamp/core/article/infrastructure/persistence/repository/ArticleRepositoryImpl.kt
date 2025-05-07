package dev.codeswamp.core.article.infrastructure.persistence.repository

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.repository.ArticleRepository
import org.springframework.stereotype.Repository

@Repository
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