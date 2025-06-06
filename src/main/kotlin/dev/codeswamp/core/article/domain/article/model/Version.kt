package dev.codeswamp.core.article.domain.article.model

import java.time.Instant

data class Version private constructor(
    val id: Long,
    val state: VersionState,

    val articleId: Long,
    val previousVersionId: Long?,

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
            diff : String,
            createdAt: Instant,
        ) = Version (
            id = id,
            state = state,
            articleId = articleId,
            previousVersionId = previousVersionId,
            diff = diff,
            createdAt = createdAt,
        )

        fun from (
            id: Long,
            state: VersionState,
            articleId: Long,
            previousVersionId: Long?,
            diff : String,
            createdAt: Instant,
            isBaseVersion: Boolean,
            fullContent: String?,
        ) = Version (
            id = id,
            state = state,
            articleId = articleId,
            previousVersionId = previousVersionId,
            diff = diff,
            createdAt = createdAt,
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
