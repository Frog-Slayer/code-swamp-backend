package dev.codeswamp.articlecommand.domain.article.model

import dev.codeswamp.articlecommand.domain.article.exception.OverwritingSnapshotException
import dev.codeswamp.articlecommand.domain.article.model.vo.Snapshot
import dev.codeswamp.articlecommand.domain.article.model.vo.Title
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

    val snapshot: Snapshot? = null,
) {
    companion object {
        fun create(
            id: Long,
            articleId: Long,
            previousVersionId: Long?,
            title: String?,
            diff: String,
            createdAt: Instant,
        ) = Version(
            id = id,
            state = VersionState.NEW,
            articleId = articleId,
            previousVersionId = previousVersionId,
            title = Title.Companion.of(title),
            diff = diff,
            createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS),
        )

        fun from(
            id: Long,
            state: VersionState,
            articleId: Long,
            previousVersionId: Long?,
            title: String?,
            diff: String,
            createdAt: Instant,
            fullContent: String?,
        ) = Version(
            id = id,
            state = state,
            articleId = articleId,
            previousVersionId = previousVersionId,
            title = Title.Companion.of(title),
            diff = diff,
            createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS),
            snapshot = fullContent?.let { Snapshot(fullContent)}
        )
    }

    fun withSnapshot(fullContent: String): Version {
        if (snapshot != null) throw OverwritingSnapshotException("Snapshot already exists")
        return this.copy(snapshot = Snapshot(fullContent))
    }

    fun publish() = this.copy(state = VersionState.PUBLISHED)
    fun archive() = this.copy(state = VersionState.ARCHIVED)
    fun draft() = this.copy(state = VersionState.DRAFT)
}
