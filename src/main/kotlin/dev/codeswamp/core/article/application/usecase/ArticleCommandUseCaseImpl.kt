package dev.codeswamp.core.article.application.usecase

import dev.codeswamp.core.article.application.dto.command.CreateArticleCommand
import dev.codeswamp.core.article.application.usecase.create.CreateArticleUseCase
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.core.article.domain.article.model.vo.Slug
import dev.codeswamp.core.article.domain.article.model.vo.Title
import dev.codeswamp.core.article.domain.support.IdGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class ArticleCommandUseCaseFacade(
    private val createArticleUseCase: CreateArticleUseCase,

) {

    fun create(createArticleCommand: CreateArticleCommand) = createArticleUseCase.handle(createArticleCommand)


}