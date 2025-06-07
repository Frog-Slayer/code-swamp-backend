package dev.codeswamp.core.article.application.usecase.query

import dev.codeswamp.core.article.application.usecase.query.read.ReadArticleResult
import dev.codeswamp.core.article.application.usecase.query.read.byid.GetPublishedArticleByIdQuery
import dev.codeswamp.core.article.application.usecase.query.read.byid.GetPublishedArticleByIdUseCase
import dev.codeswamp.core.article.application.usecase.query.read.byslug.GetPublishedArticleBySlugQuery
import dev.codeswamp.core.article.application.usecase.query.read.byslug.GetPublishedArticleBySlugUseCase
import dev.codeswamp.core.article.application.usecase.query.read.withversion.GetVersionedArticleQuery
import dev.codeswamp.core.article.application.usecase.query.read.withversion.GetVersionedArticleUseCase
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import org.springframework.stereotype.Service

@Service
class ArticleQueryUseCaseFacade(
    private val getVersionedArticleUseCase: GetVersionedArticleUseCase,
    private val getPublishedArticleByIdUseCase: GetPublishedArticleByIdUseCase,
    private val getPublishedArticleBySlugUseCase: GetPublishedArticleBySlugUseCase,
) {
    fun getVersionedArticle (query: GetVersionedArticleQuery): ReadArticleResult{
        return getVersionedArticleUseCase.handle(query)
    }

    fun getPublishedArticleById(query: GetPublishedArticleByIdQuery): ReadArticleResult{
        return getPublishedArticleByIdUseCase.handle(query)
    }

    fun getPublishedArticleBySlug(query: GetPublishedArticleBySlugQuery): ReadArticleResult {
        return getPublishedArticleBySlugUseCase.handle(query)
    }
}