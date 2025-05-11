package dev.codeswamp.core.article.infrastructure

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.repository.ArticleRepository
import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.ArticleJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ArticleRepositoryImpl (
    private val articleJpaRepository: ArticleJpaRepository,
) : ArticleRepository {

    override fun save(article: Article): Article {
        val existingArticleEntity = article.id?.let { articleJpaRepository.findById(it).orElse(null) }
        //노드 생성 필요

        val articleEntity = ArticleEntity(
            id = article.id,
            title = article.title,
            authorId = article.authorId,
            currentVersion =  article.currentVersion ?: 0,
            content = article.content,
            createdAt = article.createdAt,
            updatedAt = article.updatedAt,
            isPublic = article.isPublic,
            views = existingArticleEntity?.views ?: mutableListOf(),
            comments = existingArticleEntity?.comments ?: mutableListOf(),
            folderId = article.folderId,
        )

        return articleJpaRepository.save(articleEntity).toDomain()
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
}