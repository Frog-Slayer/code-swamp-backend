package dev.codeswamp.articlequery.presentation.dto.response

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import dev.codeswamp.articlequery.application.dto.EnrichedArticle
import java.time.Instant

data class EnrichedArticleResponse (
    @JsonSerialize(using = ToStringSerializer::class)
    val id: Long?,
    val author: UserProfileResponse?,
    val folder: FolderResponse?,

    @JsonSerialize(using = ToStringSerializer::class)
    val versionId: Long?,

    @JsonSerialize(using = ToStringSerializer::class)
    val createdAt: Instant?,
    @JsonSerialize(using = ToStringSerializer::class)
    val updatedAt: Instant?,

    val summary: String?,
    val thumbnailUrl: String?,
    val isPublic: Boolean?,
    val slug: String?,
    val title: String?,
    val content: String?,
) {
    companion object {
        fun from(article: EnrichedArticle) = EnrichedArticleResponse(
            id = article.id,
            author = UserProfileResponse.from(article.author),
            folder = FolderResponse.from(article.folder),
            versionId = article.versionId,
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