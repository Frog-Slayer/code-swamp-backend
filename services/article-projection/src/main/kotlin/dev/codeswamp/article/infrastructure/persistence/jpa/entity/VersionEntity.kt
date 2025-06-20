package dev.codeswamp.article.infrastructure.persistence.jpa.entity

import dev.codeswamp.article.domain.article.model.Version
import jakarta.persistence.*
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

    val title: String?,

    @Column(nullable = false, columnDefinition = "TEXT")
    val diff: String,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant,

    @Enumerated(EnumType.STRING)
    var state: VersionStateJpa,

    val isBaseVersion: Boolean

) {
    companion object {
        fun from(version: Version) = VersionEntity(
            id = version.id,
            articleId = version.articleId,
            previousVersionId = version.previousVersionId,
            diff =  version.diff,
            createdAt = version.createdAt,
            state = VersionStateJpa.fromDomain(version.state),
            isBaseVersion = version.isBaseVersion,
            title = version.title?.value
        )
    }

    fun toDomain(fullContent: String?): Version {
        return  Version.Companion.from(
            id = id,
            articleId = articleId,
            previousVersionId = previousVersionId,
            diff = diff,
            createdAt = createdAt,
            state = state.toDomain(),
            isBaseVersion =  isBaseVersion,
            fullContent = fullContent,
            title = title
        )
    }

    fun updateTo(newVersion: VersionEntity) {
        state = newVersion.state
    }
}
