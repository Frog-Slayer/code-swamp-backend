package dev.codeswamp.article.infrastructure.persistence.jpa.entity

import dev.codeswamp.article.application.readmodel.model.PublishedArticle
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.Instant

@Entity
data class PublishedArticleEntity (
    @Id
    val id: Long,
    val authorId: Long,
    val folderId: Long,

    val createdAt: Instant,
    val updateAt: Instant,

    val summary: String,
    val thumbnailUrl: String? = null,
    val isPublic : Boolean,

    val slug: String,
    val title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,
){
    companion object {
        fun from(publishedArticle: PublishedArticle) =  PublishedArticleEntity(
            id = publishedArticle.id,
            authorId = publishedArticle.authorId,
            folderId = publishedArticle.folderId,
            createdAt = publishedArticle.createdAt,
            updateAt = publishedArticle.updateAt,
            summary = publishedArticle.summary,
            thumbnailUrl = publishedArticle.thumbnailUrl,
            isPublic = publishedArticle.isPublic,
            slug = publishedArticle.slug,
            title = publishedArticle.title,
            content = publishedArticle.content
        )
    }

    fun toDomain() = PublishedArticle.Companion.from(
            id = id,
            authorId = authorId,
            folderId = folderId,
            createdAt = createdAt,
            updateAt = updateAt,
            summary = summary,
            thumbnailUrl = thumbnailUrl,
            isPublic = isPublic,
            slug = slug,
            title = title,
            content = content
        )
}
