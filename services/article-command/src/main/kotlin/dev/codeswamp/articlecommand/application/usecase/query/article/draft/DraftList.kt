package dev.codeswamp.articlecommand.application.usecase.query.article.draft

import java.time.Instant

data class DraftListItem (
    val articleId: Long,
    val versionId: Long,
    val createdAt: Instant,
    val title: String,
)

data class DraftList (
    val drafts : List<DraftListItem>
)