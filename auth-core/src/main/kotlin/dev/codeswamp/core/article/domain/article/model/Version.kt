package dev.codeswamp.core.article.domain.article.model

import dev.codeswamp.core.article.domain.article.model.vo.Title
import java.time.Instant
import java.time.temporal.ChronoUnit

data class Version private constructor(
    val id: Long,
    val state: VersionState,

    val articleId: Long,
    val previousVersionId: Long?,

    val title: Title?,
    val diff: String,
    val createdAt: Instant,

    val isBaseVersion: Boolean = false,
    val fullContent: String? = null,
){
    companion object {
        fun of(
            id: Long,
            state: VersionState,
            articleId: Long,
            previousVersionId: Long?,
            title: String?,
            diff : String,
            createdAt: Instant,
        ) = Version (
            id = id,
            state = state,
            articleId = articleId,
            previousVersionId = previousVersionId,
            title = Title.of(title),
            diff = diff,
            createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS),
        )

        fun from (
            id: Long,
            state: VersionState,
            articleId: Long,
            previousVersionId: Long?,
            title: String?,
            diff : String,
            createdAt: Instant,
            isBaseVersion: Boolean,
            fullContent: String?,
        ) = Version (
            id = id,
            state = state,
            articleId = articleId,
            previousVersionId = previousVersionId,
            title = Title.of(title),
            diff = diff,
            createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS),
            isBaseVersion = isBaseVersion,
            fullContent = fullContent
        )
    }

    fun asBaseVersion(fullContent: String) : Version {
        return this.copy(isBaseVersion = true, fullContent = fullContent)
    }

    fun publish() = this.copy(state = VersionState.PUBLISHED)
    fun archive() = this.copy(state = VersionState.ARCHIVED)
    fun draft() = this.copy(state = VersionState.DRAFT)
}
