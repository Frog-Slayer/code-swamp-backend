package dev.codeswamp.article.application.usecase.query.article

import dev.codeswamp.article.application.usecase.query.article.read.ReadArticleResult
import dev.codeswamp.article.application.usecase.query.article.read.byid.GetPublishedArticleByIdQuery
import dev.codeswamp.article.application.usecase.query.article.read.byid.GetPublishedArticleByIdUseCase
import dev.codeswamp.article.application.usecase.query.article.read.byslug.GetPublishedArticleBySlugQuery
import dev.codeswamp.article.application.usecase.query.article.read.byslug.GetPublishedArticleBySlugUseCase
import dev.codeswamp.article.application.usecase.query.article.read.withversion.GetVersionedArticleQuery
import dev.codeswamp.article.application.usecase.query.article.read.withversion.GetVersionedArticleUseCase
import org.springframework.stereotype.Service

@Service
class ArticleQueryUseCaseFacade(
    private val getVersionedArticleUseCase: GetVersionedArticleUseCase,
    private val getPublishedArticleByIdUseCase: GetPublishedArticleByIdUseCase,
    private val getPublishedArticleBySlugUseCase: GetPublishedArticleBySlugUseCase,
) {
    fun getVersionedArticle (query: GetVersionedArticleQuery): ReadArticleResult {
        return getVersionedArticleUseCase.handle(query)
    }

    fun getPublishedArticleById(query: GetPublishedArticleByIdQuery): ReadArticleResult {
        return getPublishedArticleByIdUseCase.handle(query)
    }

    fun getPublishedArticleBySlug(query: GetPublishedArticleBySlugQuery): ReadArticleResult {
        return getPublishedArticleBySlugUseCase.handle(query)
    }
}