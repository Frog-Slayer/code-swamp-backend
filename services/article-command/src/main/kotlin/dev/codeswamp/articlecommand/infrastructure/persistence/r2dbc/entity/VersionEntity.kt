package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity

import dev.codeswamp.articlecommand.domain.article.model.Version
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table(name = "versions")
data class VersionEntity(
    @Id//도메인에서 생성
    val id: Long,

    @Column("article_id")
    val articleId: Long,

    @Column("prev_version_id")
    val previousVersionId: Long?,

    val title: String?,

    @Column("diff")
    val diff: String,

    @Column("created_at")
    val createdAt: Instant,

    @Column("state")
    var state: VersionStateJpa,

    @Column("is_base")
    val isBaseVersion: Boolean

) {
    companion object {
        fun from(version: Version) = VersionEntity(
            id = version.id,
            articleId = version.articleId,
            previousVersionId = version.previousVersionId,
            diff = version.diff,
            createdAt = version.createdAt,
            state = VersionStateJpa.fromDomain(version.state),
            isBaseVersion = version.isBaseVersion,
            title = version.title?.value
        )
    }

    fun toDomain(fullContent: String?): Version {
        return Version.Companion.from(
            id = id,
            articleId = articleId,
            previousVersionId = previousVersionId,
            diff = diff,
            createdAt = createdAt,
            state = state.toDomain(),
            isBaseVersion = isBaseVersion,
            fullContent = fullContent,
            title = title
        )
    }
}
