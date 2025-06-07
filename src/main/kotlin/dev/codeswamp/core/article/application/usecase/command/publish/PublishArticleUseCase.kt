package dev.codeswamp.core.article.application.usecase.command.publish

interface PublishArticleUseCase {
    fun handle(command: PublishArticleCommand) : PublishArticleResult
}