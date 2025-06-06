package dev.codeswamp.core.article.application.usecase.command.create

data class CreateArticleResult (
    val articleId: Long,
    val versionId: Long,
)