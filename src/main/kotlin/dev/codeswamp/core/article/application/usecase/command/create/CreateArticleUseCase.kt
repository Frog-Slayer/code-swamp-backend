package dev.codeswamp.core.article.application.usecase.command.create

interface CreateArticleUseCase {
    fun handle(command: CreateArticleCommand) : CreateArticleResult
}