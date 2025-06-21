package dev.codeswamp.article.infrastructure.persistence.repositoryImpl

import dev.codeswamp.article.domain.article.model.Version
import dev.codeswamp.article.domain.article.model.VersionedArticle
import dev.codeswamp.article.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.article.domain.article.model.vo.Slug
import dev.codeswamp.article.domain.article.repository.ArticleRepository
import dev.codeswamp.article.domain.article.repository.VersionRepository
import dev.codeswamp.article.infrastructure.exception.article.ArticleVersionMismatchException
import dev.codeswamp.article.infrastructure.persistence.jpa.entity.ArticleMetadataEntity
import dev.codeswamp.article.infrastructure.persistence.jpa.repository.ArticleMetadataJpaRepository
import dev.codeswamp.article.infrastructure.persistence.jpa.repository.VersionJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ArticleRepositoryImpl(
    private val articleMetadataJpaRepository: ArticleMetadataJpaRepository,
    private val versionJpaRepository: VersionJpaRepository,
    private val versionRepository: VersionRepository
) : ArticleRepository {

    override fun save(versionedArticle: VersionedArticle): VersionedArticle {
        val savedMetadataEntity = saveMetadata(versionedArticle)
        val savedVersion = saveVersion(versionedArticle.currentVersion)

        return toDomain(savedMetadataEntity, savedVersion)
    }

    override fun findIdByFolderIdAndSlug(folderId: Long, slug: String): Long? {
        return articleMetadataJpaRepository.findIdByFolderIdAndSlug(folderId, slug)
    }

    override fun findByIdAndVersionId(articleId: Long, versionId: Long): VersionedArticle? {
        val savedMetadataEntity = articleMetadataJpaRepository.findByIdOrNull(articleId)
        val savedVersion = versionRepository.findByIdOrNull(versionId)

        if (savedMetadataEntity == null || savedVersion == null) return null

        if (savedMetadataEntity.id != savedVersion.articleId) throw ArticleVersionMismatchException(
            savedMetadataEntity.id,
            savedVersion.articleId
        )

        return toDomain(savedMetadataEntity, savedVersion)
    }

    override fun findPreviousPublishedVersion(articleId: Long, versionId: Long): Version? {
        return versionRepository.findPreviousPublishedVersion(articleId, versionId)
    }

    override fun findVersionByVersionId(versionId: Long): Version? {
        return versionRepository.findByIdOrNull(versionId)
    }

    override fun countVersionsOfArticle(articleId: Long): Long {
        return versionJpaRepository.countByArticleId(articleId)
    }

    override fun saveVersion(version: Version): Version {
        return versionRepository.save(version)
    }

    override fun deleteAllByFolderIdIn(folderIds: List<Long>) {
        val articleIds = articleMetadataJpaRepository.findAllIdsByFolderIdIn(folderIds)
        articleMetadataJpaRepository.deleteAllByIdIn(articleIds)
        versionRepository.deleteAllByArticleIdIn(articleIds)
    }

    private fun saveMetadata(article: VersionedArticle): ArticleMetadataEntity {
        val entity = ArticleMetadataEntity.Companion.from(article)
        return articleMetadataJpaRepository.findByIdOrNull(entity.id)
            ?.apply { updateTo(entity) }
            ?: articleMetadataJpaRepository.save(entity)
    }

    override fun deleteById(id: Long) {
        articleMetadataJpaRepository.deleteById(id)
        versionJpaRepository.deleteAllByArticleId(id)
    }

    private fun toDomain(metadata: ArticleMetadataEntity, version: Version): VersionedArticle {
        return VersionedArticle.Companion.of(
            id = metadata.id,
            authorId = metadata.authorId,
            createdAt = metadata.createdAt,
            isPublished = metadata.isPublished,
            metadata = ArticleMetadata(
                folderId = metadata.folderId,
                summary = metadata.summary,
                thumbnailUrl = metadata.thumbnailUrl,
                isPublic = metadata.isPublic,
                slug = Slug.Companion.of(metadata.slug)
            ),
            currentVersion = version
        )
    }
}