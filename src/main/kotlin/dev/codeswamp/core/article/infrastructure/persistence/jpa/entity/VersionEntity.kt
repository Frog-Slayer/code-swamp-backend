package dev.codeswamp.core.article.infrastructure.persistence.jpa.entity

import dev.codeswamp.core.article.domain.article.model.Version
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "versions")
data class VersionEntity (
    @Id//도메인에서 생성
    val id: Long,

    @JoinColumn(name = "article_id", nullable = false)
    val articleId: Long,

    @JoinColumn(name = "prev_id")
    val previousVersionId: Long?,

    @Column(nullable = false, columnDefinition = "TEXT")
    val diff: String,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant,

    @Enumerated(EnumType.STRING)
    val state: ArticleStatusJpa
    ) {
    companion object {
        fun from(version: Version) = VersionEntity(
            id = version.id,
            articleId = version.articleId,
            previousVersionId = version.previousVersionId,
            diff =  version.diff,
            createdAt = version.createdAt,
            state = ArticleStatusJpa.fromDomain(version.state),
        )
    }

    fun toDomain(): Version {
        return  Version(
            id = id,
            articleId = articleId,
            previousVersionId = previousVersionId,
            diff = diff,
            createdAt = createdAt,
            state = state.toDomain()
        )
    }
}
