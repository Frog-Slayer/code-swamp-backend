package dev.codeswamp.articlecommand.application.usecase.query.article.draft

interface GetDraftsUseCase {
    suspend fun handle(query: GetDraftsQuery): DraftList
}