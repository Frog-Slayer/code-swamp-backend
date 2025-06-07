package dev.codeswamp.core.article.application.usecase.command.publish

interface PublishArticleUseCase {
    fun create(command: CreatePublishCommand) : PublishArticleResult
    fun update(command: UpdatePublishCommand) : PublishArticleResult
}