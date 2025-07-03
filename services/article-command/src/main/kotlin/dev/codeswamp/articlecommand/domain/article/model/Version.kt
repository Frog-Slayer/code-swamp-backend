package dev.codeswamp.articlecommand.domain.article.model

import dev.codeswamp.articlecommand.domain.article.model.vo.Title
import java.time.Instant
import java.time.temporal.ChronoUnit

data class Version private constructor(
    val id: Long,

    val ownerId: Long,
    val articleId: Long,
    val parentId: Long?,

    val state: VersionState,

    val title: Title?,
    val diff: String,

    val createdAt: Instant,
) {
    companion object {
        fun create(
            id: Long,
            ownerId: Long,
            articleId: Long,
            parentId: Long?,
            title: String?,
            diff: String,
            createdAt: Instant,
        ) = Version(
            id = id,
            ownerId = ownerId,
            state = VersionState.NEW,
            articleId = articleId,
            parentId = parentId,
            title = Title.Companion.of(title),
            diff = diff,
            createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS),
        )

        fun from(
            id: Long,
            ownerId: Long,
            state: VersionState,
            articleId: Long,
            parentId: Long?,
            title: String?,
            diff: String,
            createdAt: Instant,
        ) = Version(
            id = id,
            ownerId = ownerId,
            state = state,
            articleId = articleId,
            parentId = parentId,
            title = Title.Companion.of(title),
            diff = diff,
            createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS),
        )
    }

    fun publish() = this.copy(state = VersionState.PUBLISHED)
    fun archive() = this.copy(state = VersionState.ARCHIVED)
    fun draft() = this.copy(state = VersionState.DRAFT)
}
