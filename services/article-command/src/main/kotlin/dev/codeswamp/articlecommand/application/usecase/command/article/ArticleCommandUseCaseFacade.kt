package dev.codeswamp.articlecommand.application.usecase.command.article

import dev.codeswamp.articlecommand.application.usecase.command.article.draft.CreateDraftCommand
import dev.codeswamp.articlecommand.application.usecase.command.article.draft.DraftArticleResult
import dev.codeswamp.articlecommand.application.usecase.command.article.draft.DraftArticleUseCase
import dev.codeswamp.articlecommand.application.usecase.command.article.draft.UpdateDraftCommand
import dev.codeswamp.articlecommand.application.usecase.command.article.publish.CreatePublishCommand
import dev.codeswamp.articlecommand.application.usecase.command.article.publish.PublishArticleResult
import dev.codeswamp.articlecommand.application.usecase.command.article.publish.PublishArticleUseCase
import dev.codeswamp.articlecommand.application.usecase.command.article.publish.UpdatePublishCommand
import org.springframework.stereotype.Service

@Service
class ArticleCommandUseCaseFacade(
    private val publishArticleUseCase: PublishArticleUseCase,
    private val draftArticleUseCase: DraftArticleUseCase,
) {
    suspend fun createPublish(command: CreatePublishCommand): PublishArticleResult {
        return publishArticleUseCase.create(command)
    }

    suspend fun updatePublish(command: UpdatePublishCommand): PublishArticleResult {
        return publishArticleUseCase.update(command)
    }

    suspend fun createDraft(command: CreateDraftCommand): DraftArticleResult {
        return draftArticleUseCase.create(command)
    }

    suspend fun updateDraft(command: UpdateDraftCommand): DraftArticleResult {
        return draftArticleUseCase.update(command)
    }
}