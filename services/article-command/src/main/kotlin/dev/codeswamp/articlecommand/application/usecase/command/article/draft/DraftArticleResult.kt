package dev.codeswamp.articlecommand.application.usecase.command.article.draft

data class DraftArticleResult(
    val articleId: Long,
    val versionId: Long,
)