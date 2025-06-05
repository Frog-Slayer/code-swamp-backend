package dev.codeswamp.core.article.domain.article.model

import java.time.Instant

data class Version (
    val id: Long,
    val state: ArticleState,

    val articleId: Long,
    val previousVersionId: Long?,

    val diff: String,
    val createdAt: Instant,
){
    fun publish() = this.copy(state = ArticleState.PUBLISHED)
    fun archive() = this.copy(state = ArticleState.ARCHIVED)
    fun draft() = this.copy(state = ArticleState.DRAFT)
}
