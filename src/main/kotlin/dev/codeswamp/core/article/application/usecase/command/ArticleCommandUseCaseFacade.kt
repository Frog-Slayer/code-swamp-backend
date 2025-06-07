package dev.codeswamp.core.article.application.usecase.command

import dev.codeswamp.core.article.application.usecase.command.draft.CreateDraftCommand
import dev.codeswamp.core.article.application.usecase.command.draft.UpdateDraftCommand
import dev.codeswamp.core.article.application.usecase.command.draft.DraftArticleResult
import dev.codeswamp.core.article.application.usecase.command.draft.DraftArticleUseCase
import dev.codeswamp.core.article.application.usecase.command.publish.CreatePublishCommand
import dev.codeswamp.core.article.application.usecase.command.publish.PublishArticleResult
import dev.codeswamp.core.article.application.usecase.command.publish.PublishArticleUseCase
import dev.codeswamp.core.article.application.usecase.command.publish.UpdatePublishCommand
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