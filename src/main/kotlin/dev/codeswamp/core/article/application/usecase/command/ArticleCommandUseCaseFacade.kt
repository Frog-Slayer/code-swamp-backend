package dev.codeswamp.core.article.application.usecase.command

import dev.codeswamp.core.article.application.usecase.command.create.CreateArticleCommand
import dev.codeswamp.core.article.application.usecase.command.create.CreateArticleUseCase
import org.springframework.stereotype.Service

@Service
class ArticleCommandUseCaseFacade(
    private val createArticleUseCase: CreateArticleUseCase,

    ) {

    fun create(createArticleCommand: CreateArticleCommand) = createArticleUseCase.handle(createArticleCommand)


}