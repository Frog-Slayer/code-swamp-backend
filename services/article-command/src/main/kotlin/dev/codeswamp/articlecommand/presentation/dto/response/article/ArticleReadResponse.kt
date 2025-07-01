package dev.codeswamp.articlecommand.presentation.dto.response.article

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import dev.codeswamp.articlecommand.application.usecase.query.article.versionedarticle.ReadArticleResult
import java.time.Instant

data class ArticleReadResponse(
    @JsonSerialize(using = ToStringSerializer::class)
    val id: Long,

    @JsonSerialize(using = ToStringSerializer::class)
    val authorId: Long,
    val createdAt: Instant,
    val updatedAt: Instant,

    @JsonSerialize(using = ToStringSerializer::class)
    val folderId: Long,

    val summary: String,
    val thumbnailUrl: String? = null,
    val isPublic: Boolean,
    val title: String,
    val content: String,
) {
    companion object {
        fun from(result: ReadArticleResult) = ArticleReadResponse(
            id = result.id,
            authorId = result.authorId,
            createdAt = result.createdAt,
            updatedAt = result.updatedAt,
            folderId = result.folderId,
            summary = result.summary,
            thumbnailUrl = result.thumbnailUrl,
            isPublic = result.isPublic,
            title = result.title,
            content = result.content
        )
    }
}