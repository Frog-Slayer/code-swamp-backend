package dev.codeswamp.articlequery.application.dto

import dev.codeswamp.articlequery.application.readmodel.model.Folder
import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle
import dev.codeswamp.articlequery.application.readmodel.model.UserProfile
import java.time.Instant

data class EnrichedArticle (
    val id: Long?,
    val author: UserProfile?,
    val folder: Folder?,
    val versionId: Long?,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val summary: String?,
    val thumbnailUrl: String?,
    val isPublic: Boolean?,
    val slug: String?,
    val title: String?,
    val content: String?,
)  {
    companion object {
        fun from(article: PublishedArticle, author: UserProfile?, folder: Folder?) = EnrichedArticle(
            id = article.id,
            author = author,
            folder = folder,
            versionId = article.id,
            createdAt = article.createdAt,
            updatedAt = article.updatedAt,
            summary = article.summary,
            thumbnailUrl = article.thumbnailUrl,
            isPublic = article.isPublic,
            slug = article.slug,
            title = article.title,
            content = article.content,
        )
    }
}