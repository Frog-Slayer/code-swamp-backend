package dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.entity

import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle
import dev.codeswamp.databasequery.ColumnName
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

data class PublishedArticleEntity(
    @ColumnName("id")
    val id: Long?,

    @ColumnName("author_id")
    val authorId: Long?,

    @ColumnName("folder_id")
    val folderId: Long?,

    @ColumnName("version_id")
    val versionId: Long?,

    @ColumnName("created_at")
    val createdAt: Instant?,

    @ColumnName("updated_at")
    val updatedAt: Instant?,

    @ColumnName("summary")
    val summary: String?,

    @ColumnName("thumbnail")
    val thumbnail: String?,

    @ColumnName("is_public")
    val isPublic: Boolean?,

    @ColumnName("slug")
    val slug: String?,

    @ColumnName("title")
    val title: String?,

    @ColumnName("content")
    val content: String?
) {
    fun toDomain() = PublishedArticle.Companion.from(
        id = id,
        versionId = versionId,
        authorId = authorId,
        folderId = folderId,
        createdAt = createdAt,
        updatedAt = updatedAt,
        summary = summary,
        thumbnailUrl = thumbnail,
        isPublic = isPublic,
        slug = slug,
        title = title,
        content = content
    )
}