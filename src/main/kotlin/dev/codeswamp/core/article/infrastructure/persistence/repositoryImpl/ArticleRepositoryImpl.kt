package dev.codeswamp.core.article.infrastructure.persistence.repositoryImpl

import dev.codeswamp.core.article.domain.article.model.Article
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.ArticleEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.ArticleJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ArticleRepositoryImpl (
    private val articleJpaRepository: ArticleJpaRepository,
) : ArticleRepository {

    override fun save(article: Article): Article {
        val existingArticleEntity = article.id?.let { articleJpaRepository.findById(it).orElse(null) }
        //노드 생성 필요

        val articleEntity = ArticleEntity(

        )

        val saved = articleJpaRepository.save(articleEntity).toDomain()

        return saved
    }

    override fun deleteById(id: Long) {
        articleJpaRepository.deleteById(id)
        //노드 삭제 필요
    }

    override fun findAllByIds(articleIds: List<Long>): List<Article> {
        return articleJpaRepository.findAllByIdIsIn(articleIds).map { it.toDomain() }
    }

    override fun findById(articleId: Long): Article? {
        return articleJpaRepository.findById(articleId)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findByFolderIdAndSlug(folderId: Long, slug: String): Article? {
        return articleJpaRepository.findByFolderIdAndSlug(folderId, slug)?.toDomain()
    }
}