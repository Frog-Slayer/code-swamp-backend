package dev.codeswamp.core.article.infrastructure.persistence.repository

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.model.ArticleType
import dev.codeswamp.core.article.domain.repository.ArticleRepository
import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleContentEntity
import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleMetadataEntity
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class ArticleRepositoryImpl (
    private val articleJpaRepository: ArticleJpaRepository,
    private val articleDiffJpaRepository: ArticleDiffJpaRepository,
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

    override fun findById(articleId: Long): Article? {
        TODO("Not yet implemented")
    }

}