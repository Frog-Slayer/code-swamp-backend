package dev.codeswamp.core.article.application.dto.command

data class ArchiveArticleCommand(
    val userId: Long,
    val articleId: Long,
    val versionId: Long,
)