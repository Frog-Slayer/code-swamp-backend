package dev.codeswamp.core.article.application.usecase

import dev.codeswamp.core.article.application.usecase.create.CreateArticleCommand
import dev.codeswamp.core.article.application.usecase.create.CreateArticleUseCase
import org.springframework.stereotype.Service

@Service
class ArticleCommandUseCaseFacade(
    private val createArticleUseCase: CreateArticleUseCase,

) {

    fun create(createArticleCommand: CreateArticleCommand) = createArticleUseCase.handle(createArticleCommand)


}