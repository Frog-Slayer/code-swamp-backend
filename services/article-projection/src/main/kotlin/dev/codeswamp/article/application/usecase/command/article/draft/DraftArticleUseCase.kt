package dev.codeswamp.article.application.usecase.command.article.draft

interface DraftArticleUseCase {
    fun create(command: CreateDraftCommand): DraftArticleResult
    fun update(command: UpdateDraftCommand): DraftArticleResult
}