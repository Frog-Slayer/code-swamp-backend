package dev.codeswamp.articlequery.application.readmodel.repository

import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle
import java.time.Instant

interface PublishedArticleRepository {
    suspend fun findByArticleId(userId: Long?, articleId: Long, fields: Set<String>): PublishedArticle?
    suspend fun findAllByAuthorId(userId: Long?, authorId: Long, fields: Set<String>): List<PublishedArticle>
    suspend fun findByFolderIdAndSlug(userId: Long?, folderId: Long, slug: String, fields: Set<String>): PublishedArticle?
    suspend fun findRecentArticles(
        userId: Long?,
        lastCreatedAt: Instant?,
        lastArticleId: Long?,
        limit: Int,
        fields: Set<String>,
    ) : List<PublishedArticle>
}