package dev.codeswamp.article.application.usecase.command.article.publish

interface PublishArticleUseCase {
    fun create(command: CreatePublishCommand) : PublishArticleResult
    fun update(command: UpdatePublishCommand) : PublishArticleResult
}