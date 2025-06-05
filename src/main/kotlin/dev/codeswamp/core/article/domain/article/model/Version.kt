package dev.codeswamp.core.article.domain.article.model

import java.time.Instant

data class Version (
    val id: Long,
    val state: VersionState,

    val articleId: Long,
    val previousVersionId: Long?,

    val diff: String,
    val createdAt: Instant,
){
    fun publish() = this.copy(state = VersionState.PUBLISHED)
    fun archive() = this.copy(state = VersionState.ARCHIVED)
    fun draft() = this.copy(state = VersionState.DRAFT)
}
