package dev.codeswamp.articlecommand.application.usecase.command.article.archive

data class ArchiveArticleVersionCommand(
    val userId: Long,
    val articleId: Long,
    val versionId: Long
)