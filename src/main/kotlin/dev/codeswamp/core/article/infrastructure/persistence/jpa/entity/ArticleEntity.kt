package dev.codeswamp.core.article.infrastructure.persistence.jpa.entity

import dev.codeswamp.core.article.domain.article.model.VersionedArticle
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
data class ArticleEntity (//ArticleMetadatas
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    val authorId: Long,

    var isPublic: Boolean,

    @Column(columnDefinition = "TIMESTAMP(3)")
    val createdAt: Instant = Instant.now(),

    @Column(nullable = false, name = "folder_id")
    var folderId: Long,

    @Column(nullable = false, name = "slug")
    val slug: String,

    val summary: String = "",

    val thumbnailUrl : String? = null,
) {
    companion object {
        fun from(versionedArticle: VersionedArticle) = ArticleEntity(
            id = versionedArticle.id,
            title = versionedArticle.metadata.title,
            authorId = versionedArticle.authorId,
            createdAt = versionedArticle.createdAt,
            isPublic = versionedArticle.isPublic,
            folderId = versionedArticle.folderId,
            slug = versionedArticle.metadata.slug,
            summary = versionedArticle.summary,
            thumbnailUrl = versionedArticle.thumbnailUrl,
        )
    }

    fun toDomain(): VersionedArticle {
        return VersionedArticle(
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