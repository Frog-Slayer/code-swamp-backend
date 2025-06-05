package dev.codeswamp.core.article.application.usecase.create

interface CreateArticleUseCase {
    fun handle(command: CreateArticleCommand) : CreateArticleResult
}