package dev.codeswamp.articlecommand.application.usecase.command.article.archive

interface ArchiveArticleVersionUseCase {
    suspend fun delete(command: ArchiveArticleVersionCommand)
}