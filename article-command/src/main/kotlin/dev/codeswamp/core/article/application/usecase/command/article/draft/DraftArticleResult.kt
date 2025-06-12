package dev.codeswamp.core.article.application.usecase.command.article.draft

data class DraftArticleResult (
    val articleId: Long,
    val versionId: Long,
)