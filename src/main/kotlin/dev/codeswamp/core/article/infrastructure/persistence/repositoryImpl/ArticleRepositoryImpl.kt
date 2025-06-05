package dev.codeswamp.core.article.infrastructure.persistence.repositoryImpl

import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.ArticleMetadataEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.VersionJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ArticleRepositoryImpl (
    private val articleJpaRepository: dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.ArticleMetadataEntity,
    private val versionJpaRepository: VersionJpaRepository
) : ArticleRepository {

    override fun save(versionedArticle: VersionedArticle): VersionedArticle {
        val metadataEntity = ArticleMetadataEntity.from(versionedArticle)
        val versionEntity = VersionEntity.from(versionedArticle)


        val existingArticleEntity = versionedArticle.id?.let { articleJpaRepository.findById(it).orElse(null) }
        //노드 생성 필요


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