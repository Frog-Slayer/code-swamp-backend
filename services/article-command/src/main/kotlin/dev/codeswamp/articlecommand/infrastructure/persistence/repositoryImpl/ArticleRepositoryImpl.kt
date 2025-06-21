package dev.codeswamp.articlecommand.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlecommand.domain.article.model.Version
import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle
import dev.codeswamp.articlecommand.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.articlecommand.domain.article.model.vo.Slug
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import dev.codeswamp.articlecommand.domain.article.repository.VersionRepository
import dev.codeswamp.articlecommand.infrastructure.exception.article.ArticleVersionMismatchException
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.ArticleMetadataEntity
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository.ArticleMetadataJpaRepository
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository.VersionJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ArticleRepositoryImpl(
    private val articleMetadataJpaRepository: ArticleMetadataJpaRepository,
    private val versionJpaRepository: VersionJpaRepository,
    private val versionRepository: VersionRepository
) : ArticleRepository {

    override suspend fun save(versionedArticle: VersionedArticle): VersionedArticle {
        val savedMetadataEntity = saveMetadata(versionedArticle)
        val savedVersion = saveVersion(versionedArticle.currentVersion)

        return toDomain(savedMetadataEntity, savedVersion)
    }

    override suspend fun findIdByFolderIdAndSlug(folderId: Long, slug: String): Long? {
        return articleMetadataJpaRepository.findIdByFolderIdAndSlug(folderId, slug)
    }

    override suspend fun findByIdAndVersionId(articleId: Long, versionId: Long): VersionedArticle? {
        val savedMetadataEntity = articleMetadataJpaRepository.findById(articleId)
        val savedVersion = versionRepository.findByIdOrNull(versionId)

        if (savedMetadataEntity == null || savedVersion == null) return null

        if (savedMetadataEntity.id != savedVersion.articleId) throw ArticleVersionMismatchException(
            savedMetadataEntity.id,
            savedVersion.articleId
        )

        return toDomain(savedMetadataEntity, savedVersion)
    }

    override suspend fun findPreviousPublishedVersion(articleId: Long, versionId: Long): Version? {
        return versionRepository.findPreviousPublishedVersion(articleId, versionId)
    }

    override suspend fun findVersionByVersionId(versionId: Long): Version? {
        return versionRepository.findByIdOrNull(versionId)
    }

    override suspend fun countVersionsOfArticle(articleId: Long): Long {
        return versionJpaRepository.countByArticleId(articleId)
    }

    override suspend fun saveVersion(version: Version): Version {
        return versionRepository.save(version)
    }

    override suspend fun deleteAllByFolderIdIn(folderIds: List<Long>) {
        val articleIds = articleMetadataJpaRepository.findAllIdsByFolderIdIn(folderIds)
        articleMetadataJpaRepository.deleteAllByIdIn(articleIds)
        versionRepository.deleteAllByArticleIdIn(articleIds)
    }

    private suspend fun saveMetadata(article: VersionedArticle): ArticleMetadataEntity {
        val entity = ArticleMetadataEntity.Companion.from(article)
        return articleMetadataJpaRepository.findById(entity.id)
            ?.apply { updateTo(entity) }
            ?: articleMetadataJpaRepository.save(entity)
    }

    override suspend fun deleteById(id: Long) {
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
                thumbnailUrl = metadata.thumbnail,
                isPublic = metadata.isPublic,
                slug = Slug.Companion.of(metadata.slug)
            ),
            currentVersion = version
        )
    }
}