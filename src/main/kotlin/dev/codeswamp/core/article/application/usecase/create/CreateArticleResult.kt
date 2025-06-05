package dev.codeswamp.core.article.application.usecase.create

data class CreateArticleResult (
    val articleId: Long,
    val versionId: Long,
)