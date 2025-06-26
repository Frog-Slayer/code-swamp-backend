package dev.codeswamp.articlecommand.application.usecase.command.article.delete

data class DeleteArticleCommand(
    val userId: Long,
    val articleId: Long,
)