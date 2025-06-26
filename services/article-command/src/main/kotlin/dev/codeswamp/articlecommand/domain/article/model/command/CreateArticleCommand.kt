package dev.codeswamp.articlecommand.domain.article.model.command

import dev.codeswamp.articlecommand.domain.article.model.VersionState
import dev.codeswamp.articlecommand.domain.article.model.vo.ArticleMetadata
import java.time.Instant

data class CreateArticleCommand (
    val generateId: () -> Long,
    val authorId: Long,
    val createdAt: Instant,
    val metadata: ArticleMetadata,
    val title: String?,
    val diff: String,
)