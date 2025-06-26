package dev.codeswamp.articlecommand.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlecommand.domain.article.model.Version
import dev.codeswamp.articlecommand.domain.article.model.Article
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
    private val versionRepository: VersionRepository
) : ArticleRepository {

    override suspend fun createMetadata(article: Article) {
        val entity = ArticleMetadataEntity.Companion.from(article)
        articleMetadataR2dbcRepository.insert(
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

    override suspend fun findById(id: Long): Article? {
        val articleMetadataEntity = articleMetadataR2dbcRepository.findById(id) ?: return null
        val versions = versionRepository.findAllByArticleId(id)

        return toDomain(articleMetadataEntity, versions)
    }

    override suspend fun updateMetadata(article: Article) {
        val metadataEntity = ArticleMetadataEntity.Companion.from( article)
        articleMetadataR2dbcRepository.save(metadataEntity)
    }

    override suspend fun insertVersions(versions: List<Version>) {
        versionRepository.insertVersions(versions)
    }

    override suspend fun updateVersions(versions: List<Version>) {
        versionRepository.updateVersions(versions)
    }

    override suspend fun findIdByFolderIdAndSlug(folderId: Long, slug: String): Long? {
        return articleMetadataR2dbcRepository.findIdByFolderIdAndSlug(folderId, slug)
    }

    override suspend fun deleteAllByFolderIdIn(folderIds: List<Long>) {
        val articleIds = articleMetadataR2dbcRepository.findAllIdsByFolderIdIn(folderIds)
        articleMetadataR2dbcRepository.deleteAllByIdIn(articleIds)
        versionRepository.deleteAllByArticleIdIn(articleIds)
    }

    override suspend fun deleteById(id: Long) {
        articleMetadataR2dbcRepository.deleteById(id)
        versionRepository.deleteByArticleId(id)
    }

    private fun toDomain(metadata: ArticleMetadataEntity, versions: List<Version>): Article {
        return Article.Companion.of(
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
            versionList = versions
        )
    }
}