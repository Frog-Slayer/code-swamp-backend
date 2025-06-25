package dev.codeswamp.articlecommand.domain.article.model.command

import java.time.Instant

data class ArticleVersionUpdateCommand (
    val title: String,
    val hasMeaningfulDiff: Boolean,
    val diff: String,
    val parentVersionId : Long,
    val generateId: () -> Long,
    val createdAt: Instant,
)