package dev.codeswamp.articlecommand.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlecommand.domain.article.model.Version
import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle
import dev.codeswamp.articlecommand.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.articlecommand.domain.article.model.vo.Slug
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import dev.codeswamp.articlecommand.domain.article.repository.VersionRepository
import dev.codeswamp.articlecommand.application.exception.article.ArticleVersionMismatchException
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.ArticleMetadataEntity
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository.ArticleMetadataR2dbcRepository
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository.VersionR2dbcRepository
import org.springframework.stereotype.Repository

@Repository
class ArticleRepositoryImpl(
    private val articleMetadataR2dbcRepository: ArticleMetadataR2dbcRepository,
    private val versionR2dbcRepository: VersionR2dbcRepository,
    private val versionRepository: VersionRepository
) : ArticleRepository {

    override suspend fun create(versionedArticle: VersionedArticle): VersionedArticle {
        val savedMetadataEntity = createMetadata(versionedArticle)
        val savedVersion = versionRepository.save(versionedArticle.currentVersion)

        return toDomain(savedMetadataEntity, savedVersion)
    }

    private suspend fun createMetadata(article: VersionedArticle) : ArticleMetadataEntity {
        val entity = ArticleMetadataEntity.Companion.from(article)
        return articleMetadataR2dbcRepository.insert(
            id = entity.id,
            folderId = entity.folderId,
            slug = entity.slug,
            authorId = entity.authorId,
            isPublic = entity.isPublic,
            isPublished = entity.isPublished,
            createdAt = entity.createdAt,
            summary = entity.summary,
            thumbnail = entity.thumbnail,
        )
    }

    override suspend fun update (versionedArticle: VersionedArticle): VersionedArticle {
        updateMetadata(versionedArticle)
        versionRepository.save(versionedArticle.currentVersion)

        return versionedArticle
    }

    private suspend fun updateMetadata(versionedArticle: VersionedArticle): VersionedArticle {
        val metadataEntity = ArticleMetadataEntity.Companion.from( versionedArticle)
        articleMetadataR2dbcRepository.save(metadataEntity)
        return toDomain(metadataEntity, versionedArticle.currentVersion )
    }

    override suspend fun findIdByFolderIdAndSlug(folderId: Long, slug: String): Long? {
        return articleMetadataR2dbcRepository.findIdByFolderIdAndSlug(folderId, slug)
    }

    override suspend fun findByIdAndVersionId(articleId: Long, versionId: Long): VersionedArticle? {
        val savedMetadataEntity = articleMetadataR2dbcRepository.findById(articleId)
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
        return versionR2dbcRepository.countByArticleId(articleId)
    }


    override suspend fun deleteAllByFolderIdIn(folderIds: List<Long>) {
        val articleIds = articleMetadataR2dbcRepository.findAllIdsByFolderIdIn(folderIds)
        articleMetadataR2dbcRepository.deleteAllByIdIn(articleIds)
        versionRepository.deleteAllByArticleIdIn(articleIds)
    }


    override suspend fun deleteById(id: Long) {
        articleMetadataR2dbcRepository.deleteById(id)
        versionR2dbcRepository.deleteAllByArticleId(id)
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