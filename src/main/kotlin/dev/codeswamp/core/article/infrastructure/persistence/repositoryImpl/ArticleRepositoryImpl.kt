package dev.codeswamp.core.article.infrastructure.persistence.repositoryImpl

import com.fasterxml.jackson.core.Versioned
import dev.codeswamp.core.article.domain.article.exceptions.ArticleNotFoundException
import dev.codeswamp.core.article.domain.article.model.Version
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.core.article.domain.article.model.vo.Slug
import dev.codeswamp.core.article.domain.article.model.vo.Title
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.ArticleMetadataEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.VersionEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.ArticleMetadataJpaRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.VersionJpaRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ArticleRepositoryImpl (
    private val articleMetadataJpaRepository: ArticleMetadataJpaRepository,
    private val versionJpaRepository: VersionJpaRepository
) : ArticleRepository {

    override fun save(versionedArticle: VersionedArticle): VersionedArticle {
        val savedMetadataEntity = saveMetadata(versionedArticle)
        val savedVersionEntity = versionedArticle.currentVersion?.let { saveVersion(it)}
            ?: throw IllegalStateException("current version is null")

        return toDomain(savedMetadataEntity, savedVersionEntity)
    }

    override fun findByIdAndVersionId( articleId: Long, versionId: Long): VersionedArticle? {
        val savedMetadataEntity = articleMetadataJpaRepository.findByIdOrNull(articleId)
        val savedVersionEntity = versionJpaRepository.findByIdOrNull(versionId)

        if (savedMetadataEntity == null || savedVersionEntity == null) return null

        if (savedMetadataEntity.id != savedVersionEntity.articleId) throw RuntimeException("서로 맞지 않는 버전입니다")

        return toDomain(savedMetadataEntity, savedVersionEntity)
    }

    private fun saveMetadata(article: VersionedArticle) : ArticleMetadataEntity{
        val entity = ArticleMetadataEntity.from(article)
        return articleMetadataJpaRepository.findByIdOrNull(entity.id)
            ?.apply { updateTo(entity) }
            ?: articleMetadataJpaRepository.save(entity)
    }

    override fun saveVersion(version: Version): VersionEntity {
        val entity = VersionEntity.from(version)
        return versionJpaRepository.findByIdOrNull(entity.id)
            ?.apply { updateTo(entity) }
            ?: versionJpaRepository.save(entity)
    }

    override fun deleteById(id: Long) {
        articleMetadataJpaRepository.deleteById(id)
        versionJpaRepository.deleteAllByArticleId(id)
    }

    private fun toDomain(metadata: ArticleMetadataEntity, version:  VersionEntity): VersionedArticle {
        return VersionedArticle.of(
            id = metadata.id,
            authorId = metadata.authorId,
            createdAt = metadata.createdAt,
            isPublished = metadata.isPublished,
            metadata = ArticleMetadata(
                folderId = metadata.folderId,
                summary = metadata.summary,
                thumbnailUrl = metadata.thumbnailUrl,
                isPublic = metadata.isPublic,
                title = Title.of(metadata.title),
                slug = Slug.of(metadata.slug)
            ),
            currentVersion = version.toDomain()
        )
    }
}