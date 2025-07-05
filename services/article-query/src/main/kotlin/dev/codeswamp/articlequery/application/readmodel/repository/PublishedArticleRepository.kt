package dev.codeswamp.articlequery.application.readmodel.repository

import dev.codeswamp.articlequery.application.readmodel.dto.ArticleSummary
import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle
import java.time.Instant

interface PublishedArticleRepository {
    suspend fun findByArticleId(articleId: Long): PublishedArticle?
    suspend fun findArticleSummaryByArticleId(articleId: Long): ArticleSummary?
    suspend fun findByFolderIdAndSlug(folderId: Long, slug: String): PublishedArticle?
    suspend fun findRecentArticles(
        userId: Long?,
        lastCreatedAt: Instant?,
        lastArticleId: Long?,
        limit: Int,
    ) : List<ArticleSummary>
}