package dev.codeswamp.article.application.usecase.command.article

import dev.codeswamp.article.application.usecase.command.article.draft.CreateDraftCommand
import dev.codeswamp.article.application.usecase.command.article.draft.DraftArticleResult
import dev.codeswamp.article.application.usecase.command.article.draft.DraftArticleUseCase
import dev.codeswamp.article.application.usecase.command.article.draft.UpdateDraftCommand
import dev.codeswamp.article.application.usecase.command.article.publish.CreatePublishCommand
import dev.codeswamp.article.application.usecase.command.article.publish.PublishArticleResult
import dev.codeswamp.article.application.usecase.command.article.publish.PublishArticleUseCase
import dev.codeswamp.article.application.usecase.command.article.publish.UpdatePublishCommand
import org.springframework.stereotype.Service

@Service
class ArticleCommandUseCaseFacade(
    private val publishArticleUseCase: PublishArticleUseCase,
    private val draftArticleUseCase: DraftArticleUseCase,
) {
    fun createPublish(command: CreatePublishCommand): PublishArticleResult {
        return publishArticleUseCase.create(command)
    }

    fun updatePublish(command: UpdatePublishCommand): PublishArticleResult {
        return publishArticleUseCase.update(command)
    }

    fun createDraft(command: CreateDraftCommand): DraftArticleResult {
        return draftArticleUseCase.create(command)
    }

    fun updateDraft(command: UpdateDraftCommand): DraftArticleResult {
        return draftArticleUseCase.update(command)
    }
}