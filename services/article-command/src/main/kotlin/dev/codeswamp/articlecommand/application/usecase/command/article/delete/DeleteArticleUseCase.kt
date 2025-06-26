package dev.codeswamp.articlecommand.application.usecase.command.article.delete

interface DeleteArticleUseCase {
    suspend fun delete(command: DeleteArticleCommand)
}