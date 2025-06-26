package dev.codeswamp.articlecommand.domain.article.model.command

import dev.codeswamp.articlecommand.domain.support.DiffProcessor
import java.time.Instant

data class ArticleVersionUpdateCommand (
    val currentVersionId : Long,
    val title: String,
    val diff: String,
    val generateId: () -> Long,
    val diffProcessor: DiffProcessor,
    val createdAt: Instant,
)