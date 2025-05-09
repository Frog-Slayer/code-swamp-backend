package dev.codeswamp.core.article.infrastructure.persistence.entity

import dev.codeswamp.core.article.domain.model.ArticleDiff
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "article_diff")
data class ArticleDiffEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    val article: ArticleEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prev_id")
    val previousVersion: ArticleDiffEntity?,

    @Column(nullable = false, columnDefinition = "TEXT")
    val diffData: String,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now()
) {

    fun toDomain(): ArticleDiff {
        return  ArticleDiff(
            id = id,
            articleId = article.id!!,
            previousVersionId = previousVersion?.id,
            diffData = diffData,
            createdAt = createdAt
        )
    }
}
