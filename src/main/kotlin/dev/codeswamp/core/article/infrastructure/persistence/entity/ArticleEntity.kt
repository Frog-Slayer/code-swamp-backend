package dev.codeswamp.core.article.infrastructure.persistence.entity

import com.fasterxml.jackson.annotation.JsonFormat
import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.model.ArticleType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.time.temporal.ChronoUnit

@Entity
@Table(name = "article",
    uniqueConstraints = [UniqueConstraint(columnNames = ["folder_id", "slug"])]
)
data class ArticleEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

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

    val comments: MutableList<Long> = mutableListOf(),

    @Column(nullable = false, name = "folder_id")
    var folderId: Long,

    @OneToMany
    val views: MutableList<ArticleView> = mutableListOf(),

    @Column(nullable = false, name = "slug")
    val slug: String,

    val summary: String = "",
    val thumbnailUrl : String? = null,


) {
    fun toDomain(): Article {
        return Article(
            id = id,
            title = title,
            type = ArticleType.NEW,//TODO
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