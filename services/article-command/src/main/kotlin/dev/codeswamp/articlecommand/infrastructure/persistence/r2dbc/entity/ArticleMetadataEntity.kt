package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity

import dev.codeswamp.articlecommand.domain.article.model.Article
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table(name = "article_metadata")
data class ArticleMetadataEntity(
    @Id
    val id: Long,

    @Column("folder_id")
    var folderId: Long,
    var slug: String?,

    @Column("author_id")
    var authorId: Long,

    @Column("is_public")
    var isPublic: Boolean,

    @Column("is_published")
    var isPublished: Boolean,

    @Column("created_at")
    var createdAt: Instant = Instant.now(),

    var summary: String = "",

    var thumbnail: String? = null,
) {
    companion object {
        fun from(article: Article) = ArticleMetadataEntity(
            id = article.id,
            authorId = article.authorId,
            createdAt = article.createdAt,
            isPublished = article.isPublished,
            isPublic = article.metadata.isPublic,
            folderId = article.metadata.folderId,
            summary = article.metadata.summary,
            slug = article.metadata.slug?.value,
            thumbnail = article.metadata.thumbnailUrl,
        )
    }
}