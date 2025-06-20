package dev.codeswamp.article.application.usecase.query.article

import dev.codeswamp.article.application.usecase.query.article.read.ReadArticleResult
import dev.codeswamp.article.application.usecase.query.article.read.withversion.GetVersionedArticleQuery
import dev.codeswamp.article.application.usecase.query.article.read.withversion.GetVersionedArticleUseCase
import org.springframework.stereotype.Service

@Service
class ArticleQueryUseCaseFacade(
    private val getVersionedArticleUseCase: GetVersionedArticleUseCase,
) {
    fun getVersionedArticle (query: GetVersionedArticleQuery): ReadArticleResult {
        return getVersionedArticleUseCase.handle(query)
    }
}