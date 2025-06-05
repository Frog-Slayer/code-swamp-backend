package dev.codeswamp.core.article.infrastructure.persistence.jpa.entity

import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.model.vo.Title
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
    @Id//도메인에서 생성
    val id: Long,

    var title: String?,

    @Column(nullable = false, name = "folder_id")
    var folderId: Long,

    var slug: String?,

    @Column(nullable = false)
    var authorId: Long,

    var isPublic: Boolean,
    var isPublished: Boolean,

    @Column(columnDefinition = "TIMESTAMP(3)")
    var createdAt: Instant = Instant.now(),

    var summary: String = "",

    var thumbnailUrl : String? = null,
) {
    companion object {
        fun from(versionedArticle: VersionedArticle) = ArticleEntity(
            id = versionedArticle.id,
            authorId = versionedArticle.authorId,
            createdAt = versionedArticle.createdAt,
            isPublished = versionedArticle.isPublished,
            title = versionedArticle.metadata.title?.value,
            isPublic = versionedArticle.metadata.isPublic,
            folderId = versionedArticle.metadata.folderId,
            summary = versionedArticle.metadata.summary,
            slug = versionedArticle.metadata.slug?.value,
            thumbnailUrl = versionedArticle.metadata.thumbnailUrl,
        )
    }
}