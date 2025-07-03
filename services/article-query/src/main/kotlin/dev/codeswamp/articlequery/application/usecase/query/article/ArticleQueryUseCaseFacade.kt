package dev.codeswamp.articlequery.application.usecase.query.article

import dev.codeswamp.articlequery.application.usecase.query.article.read.ReadArticleResult
import dev.codeswamp.articlequery.application.usecase.query.article.read.byid.GetPublishedArticleByIdQuery
import dev.codeswamp.articlequery.application.usecase.query.article.read.byid.GetPublishedArticleByIdUseCase
import dev.codeswamp.articlequery.application.usecase.query.article.read.byslug.GetPublishedArticleBySlugQuery
import dev.codeswamp.articlequery.application.usecase.query.article.read.byslug.GetPublishedArticleBySlugUseCase
import org.springframework.stereotype.Service

@Service
class ArticleQueryUseCaseFacade(
    private val getPublishedArticleByIdUseCase: GetPublishedArticleByIdUseCase,
    private val getPublishedArticleBySlugUseCase: GetPublishedArticleBySlugUseCase,
) {
    suspend fun getPublishedArticleById(query: GetPublishedArticleByIdQuery): ReadArticleResult {
        return getPublishedArticleByIdUseCase.handle(query)
    }

    suspend fun getPublishedArticleBySlug(query: GetPublishedArticleBySlugQuery): ReadArticleResult {
        return getPublishedArticleBySlugUseCase.handle(query)
    }
}