package dev.codeswamp.articlequery.presentation.dto.response.article

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import dev.codeswamp.articlequery.application.usecase.query.article.list.ArticleListItem
import java.time.Instant

data class ArticleCardResponseDto(
    @JsonSerialize(using = ToStringSerializer::class)
    val id: Long,

    @JsonSerialize(using = ToStringSerializer::class)
    val authorId: Long,
    val authorName: String,
    val authorNickname: String,
    val authorProfileImage: String,

    val folderPath: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val summary: String,
    val thumbnailUrl: String?,
    val isPublic: Boolean,
    val slug: String,
    val title: String,
) {
    companion object {
        fun from(item: ArticleListItem) =  ArticleCardResponseDto(
            id = item.id,
            authorId = item.authorId,
            authorName = item.authorName,
            authorNickname = item.authorNickname,
            authorProfileImage = item.authorProfileImage,
            folderPath =  item.folderPath,
            createdAt = item.createdAt,
            updatedAt = item.updatedAt,
            summary = item.summary,
            thumbnailUrl = item.thumbnailUrl,
            isPublic = item.isPublic,
            slug = item.slug,
            title = item.title,
        )
    }
}