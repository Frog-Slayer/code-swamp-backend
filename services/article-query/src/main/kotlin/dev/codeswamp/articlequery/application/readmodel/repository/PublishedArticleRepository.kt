package dev.codeswamp.articlequery.application.readmodel.repository

import dev.codeswamp.articlequery.application.readmodel.dto.ArticleSummary
import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle
import dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.dto.ArticleListItemProjection
import java.time.Instant

interface PublishedArticleRepository {
    suspend fun findByArticleId(articleId: Long): PublishedArticle?
    suspend fun findByFolderIdAndSlug(folderId: Long, slug: String): PublishedArticle?

    suspend fun findRecentArticles(
        userId: Long?,
        lastCreatedAt: Instant?,
        lastArticleId: Long?,
        limit: Int,
    ) : List<ArticleSummary>
}