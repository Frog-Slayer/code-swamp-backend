package dev.codeswamp.core.article.application.usecase.create

import dev.codeswamp.core.article.application.dto.command.CreateArticleCommand

interface CreateArticleUseCase {
    fun handle(command: CreateArticleCommand)
}