package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity

import dev.codeswamp.articlecommand.domain.article.model.Version
import dev.codeswamp.articlecommand.domain.article.model.VersionState
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table(name = "version")
data class VersionEntity(
    @Id//도메인에서 생성
    val id: Long,

    @Column("owner_id")
    val ownerId: Long,

    @Column("article_id")
    val articleId: Long,

    @Column("parent_id")
    val parentId: Long?,

    @Column("title")
    val title: String?,

    @Column("diff")
    val diff: String,

    @Column("created_at")
    val createdAt: Instant,

    @Column("state")
    var state: String,
) {
    companion object {
        fun from(version: Version) = VersionEntity(
            id = version.id,
            ownerId = version.ownerId,
            articleId = version.articleId,
            parentId = version.parentId,
            diff = version.diff,
            createdAt = version.createdAt,
            state = version.state.name,
            title = version.title?.value
        )
    }

    fun toDomain(): Version {
        return Version.Companion.from(
            id = id,
            ownerId = ownerId,
            articleId = articleId,
            parentId = parentId,
            diff = diff,
            createdAt = createdAt,
            state = VersionState.valueOf(state),
            title = title
        )
    }
}
