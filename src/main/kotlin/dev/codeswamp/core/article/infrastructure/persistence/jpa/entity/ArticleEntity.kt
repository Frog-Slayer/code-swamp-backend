package dev.codeswamp.core.article.infrastructure.persistence.jpa.entity

import dev.codeswamp.core.article.domain.article.model.Article
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.Instant
import java.time.temporal.ChronoUnit

@Entity
@Table(name = "article",
    uniqueConstraints = [UniqueConstraint(columnNames = ["folder_id", "slug"])]
)
data class ArticleEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    val authorId: Long,

    @Column(nullable = false)
    var currentVersion: Long = 0,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,

    @Column(columnDefinition = "TIMESTAMP(3)")
    val createdAt: Instant = Instant.now(),

    @Column(columnDefinition = "TIMESTAMP(3)")
    var updatedAt: Instant = Instant.now(),

    var isPublic: Boolean,

    @Column(nullable = false, name = "folder_id")
    var folderId: Long,

    @Column(nullable = false, name = "slug")
    val slug: String,

    val summary: String = "",
    val thumbnailUrl : String? = null,

    @Enumerated(EnumType.STRING)
    val status: ArticleStatusJpa


) {
    companion object {
        fun from(article: Article) = ArticleEntity(
            id = article.id,
            title = article.title,
            authorId = article.authorId,
            currentVersion = article.currentVersion ?: 0,
            content = article.content,
            createdAt = article.createdAt,
            updatedAt = article.updatedAt,
            isPublic = article.isPublic,
            folderId = article.folderId,
            slug = article.slug,
            summary = article.summary,
            thumbnailUrl = article.thumbnailUrl,
            status = ArticleStatusJpa.fromDomain(article.status)
        )
    }

    fun toDomain(): Article {
        return Article(
            id = id,
            title = title,
            status = status.toDomain(),
            authorId = authorId,
            folderId = folderId,
            isPublic = isPublic,
            createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS),
            updatedAt = updatedAt.truncatedTo(ChronoUnit.MILLIS),
            currentVersion = currentVersion,
            content = content,
            slug = slug,
            summary = summary,
            thumbnailUrl = thumbnailUrl
        )
    }
}