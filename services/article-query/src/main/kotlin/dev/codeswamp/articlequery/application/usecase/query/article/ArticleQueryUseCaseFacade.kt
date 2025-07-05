package dev.codeswamp.articlequery.application.usecase.query.article

import dev.codeswamp.articlequery.application.usecase.query.article.list.ArticleListItem
import dev.codeswamp.articlequery.application.usecase.query.article.list.recent.GetRecentArticlesQuery
import dev.codeswamp.articlequery.application.usecase.query.article.list.recent.GetRecentArticlesUseCase
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
    private val getRecentArticlesUseCase: GetRecentArticlesUseCase
) {
    suspend fun getPublishedArticleById(query: GetPublishedArticleByIdQuery): ReadArticleResult {
        return getPublishedArticleByIdUseCase.handle(query)
    }

    suspend fun getPublishedArticleBySlug(query: GetPublishedArticleBySlugQuery): ReadArticleResult {
        return getPublishedArticleBySlugUseCase.handle(query)
    }

    suspend fun getRecentArticles(query : GetRecentArticlesQuery) : List<ArticleListItem> {
        return getRecentArticlesUseCase.getRecentArticles(query)
    }
}