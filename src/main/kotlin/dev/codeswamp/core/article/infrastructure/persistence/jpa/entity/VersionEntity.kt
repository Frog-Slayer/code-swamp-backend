package dev.codeswamp.core.article.infrastructure.persistence.jpa.entity

import dev.codeswamp.core.article.domain.article.model.Version
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "versions")
data class VersionEntity (
    @Id//도메인에서 생성
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    val article: ArticleEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prev_id")
    val previousVersion: VersionEntity?,

    @Column(nullable = false, columnDefinition = "TEXT")
    val diffData: String,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    val isSnapshot: Boolean = false,

    @Column(columnDefinition = "TEXT")
    val snapshotContent: String? = null,

    @Enumerated(EnumType.STRING)
    val state: ArticleStatusJpa
    ) {

    fun toDomain(): Version {
        return  Version(
            id = id,
            articleId = article.id,
            previousVersionId = previousVersion?.id,
            diff = diffData,
            createdAt = createdAt,
            state = state.toDomain()
        )
    }
}
