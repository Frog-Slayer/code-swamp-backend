package dev.codeswamp.articlequery.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlequery.application.readmodel.dto.ArticleSummary
import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle
import dev.codeswamp.articlequery.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.dto.ArticleSummaryDto
import dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.repository.PublishedArticleR2dbcRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.temporal.ChronoUnit

@Repository
class PublishedArticleRepositoryImpl(
    private val publishedArticleR2dbcRepository: PublishedArticleR2dbcRepository
) : PublishedArticleRepository {
    override suspend fun findByArticleId(articleId: Long): PublishedArticle? {
        return publishedArticleR2dbcRepository.findById(articleId)?.toDomain()
    }

    override suspend fun findArticleSummaryByArticleId(articleId: Long): ArticleSummary? {
        return publishedArticleR2dbcRepository.findArticleSummaryById(articleId)?.toArticleSummary()
    }

    override suspend fun findByFolderIdAndSlug(folderId: Long, slug: String): PublishedArticle? {
        return publishedArticleR2dbcRepository.findByFolderIdAndSlug(folderId, slug)?.toDomain()
    }

    override suspend fun findRecentArticles(
        userId: Long?,
        lastCreatedAt: Instant?,
        lastArticleId: Long?,
        limit: Int
    ) : List<ArticleSummary> {
        val createdAt = lastCreatedAt ?: Instant.now().plus(1, ChronoUnit.DAYS)
        val articleId = lastArticleId?:0

        return publishedArticleR2dbcRepository.findArticlesBeforeCursor(
            userId,
            createdAt,
            articleId,
            limit
        ).map{ it.toArticleSummary() }
    }


    fun ArticleSummaryDto.toArticleSummary() = ArticleSummary(
        id = id,
        versionId = versionId,
        authorId = authorId,
        folderId = folderId,
        createdAt = createdAt,
        updatedAt = updatedAt,
        summary = summary,
        thumbnail = thumbnail,
        isPublic = isPublic,
        slug = slug,
        title = title
    )
}
