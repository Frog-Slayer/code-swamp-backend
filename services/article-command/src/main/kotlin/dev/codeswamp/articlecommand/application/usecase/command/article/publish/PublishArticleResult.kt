package dev.codeswamp.articlecommand.application.usecase.command.article.publish

data class PublishArticleResult(
    val articleId: Long,
    val versionId: Long,
)