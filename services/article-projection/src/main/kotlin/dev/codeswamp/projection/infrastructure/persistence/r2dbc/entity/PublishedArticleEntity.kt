package dev.codeswamp.projection.infrastructure.persistence.r2dbc.entity

import dev.codeswamp.projection.application.readmodel.model.PublishedArticle
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
    val updateAt: Instant,

    @Column("summary")
    val summary: String,

    @Column("thumnail")
    val thumbnail: String? = null,

    @Column("is_public")
    val isPublic: Boolean,

    @Column("slug")
    val slug: String,

    @Column("title")
    val title: String,

    @Column("content")
    val content: String,
) {

    fun toDomain() = PublishedArticle(
        id = id,
        versionId = versionId,
        authorId = authorId,
        folderId = folderId,
        createdAt = createdAt,
        updatedAt = updateAt,
        summary = summary,
        thumbnail = thumbnail,
        isPublic = isPublic,
        slug = slug,
        title = title,
        content = content
    )
}
