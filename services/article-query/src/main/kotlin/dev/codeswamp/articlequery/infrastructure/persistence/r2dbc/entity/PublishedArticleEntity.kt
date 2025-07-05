package dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.entity

import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("published_articles")
data class PublishedArticleEntity(
    @Id
    val id: Long,

    @Column("version_id")
    val versionId: Long,

    @Column("author_id")
    val authorId: Long,

    @Column("folder_id")
    val folderId: Long,

    @Column("created_at")
    val createdAt: Instant,
    @Column("updated_at")
    val updatedAt: Instant,

    val summary: String,

    @Column("thumbnail")
    val thumbnail: String? = null,

    @Column("is_public")
    val isPublic: Boolean,

    val slug: String,
    val title: String,

    val content: String,
) {
    companion object {
        fun from(publishedArticle: PublishedArticle) = PublishedArticleEntity(
            id = publishedArticle.id,
            versionId = publishedArticle.versionId,
            authorId = publishedArticle.authorId,
            folderId = publishedArticle.folderId,
            createdAt = publishedArticle.createdAt,
            updatedAt = publishedArticle.updatedAt,
            summary = publishedArticle.summary,
            thumbnail = publishedArticle.thumbnailUrl,
            isPublic = publishedArticle.isPublic,
            slug = publishedArticle.slug,
            title = publishedArticle.title,
            content = publishedArticle.content
        )
    }

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
