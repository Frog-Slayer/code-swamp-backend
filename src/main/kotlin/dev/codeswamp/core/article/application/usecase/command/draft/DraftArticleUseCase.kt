package dev.codeswamp.core.article.application.usecase.command.draft

interface DraftArticleUseCase {
    fun create(command: CreateDraftCommand) : DraftArticleResult
    fun update(command: UpdateDraftCommand) : DraftArticleResult
}