package dev.codeswamp.articlecommand.application.usecase.command.article.publish

interface PublishArticleUseCase {
    suspend fun create(command: CreatePublishCommand): PublishArticleResult
    suspend fun update(command: UpdatePublishCommand): PublishArticleResult
}