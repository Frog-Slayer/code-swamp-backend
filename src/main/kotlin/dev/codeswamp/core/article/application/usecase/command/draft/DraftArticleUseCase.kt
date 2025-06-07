package dev.codeswamp.core.article.application.usecase.command.draft

interface DraftArticleUseCase {
    fun handle(command: DraftArticleCommand) : DraftArticleResult
}