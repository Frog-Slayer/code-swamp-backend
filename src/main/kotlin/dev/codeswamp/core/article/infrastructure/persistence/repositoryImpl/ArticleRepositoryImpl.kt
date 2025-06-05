package dev.codeswamp.core.article.infrastructure.persistence.repositoryImpl

import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.ArticleEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.ArticleJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ArticleRepositoryImpl (
    private val articleJpaRepository: ArticleJpaRepository,
) : ArticleRepository {

    override fun save(versionedArticle: VersionedArticle): VersionedArticle {
        val existingArticleEntity = versionedArticle.id?.let { articleJpaRepository.findById(it).orElse(null) }
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

    override fun findAllByIds(articleIds: List<Long>): List<VersionedArticle> {
        return articleJpaRepository.findAllByIdIsIn(articleIds).map { it.toDomain() }
    }

    override fun findById(articleId: Long): VersionedArticle? {
        return articleJpaRepository.findById(articleId)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findByFolderIdAndSlug(folderId: Long, slug: String): VersionedArticle? {
        return articleJpaRepository.findByFolderIdAndSlug(folderId, slug)?.toDomain()
    }
}