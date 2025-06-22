package dev.codeswamp.articlecommand.application.usecase.command.article.draft

interface DraftArticleUseCase {
    suspend fun create(command: CreateDraftCommand): DraftArticleResult
    suspend fun update(command: UpdateDraftCommand): DraftArticleResult
}