package dev.codeswamp.articlecommand.application.usecase.query.article

import dev.codeswamp.articlecommand.application.usecase.query.article.draft.DraftList
import dev.codeswamp.articlecommand.application.usecase.query.article.draft.GetDraftsQuery
import dev.codeswamp.articlecommand.application.usecase.query.article.draft.GetDraftsUseCase
import dev.codeswamp.articlecommand.application.usecase.query.article.versionedarticle.GetVersionedArticleQuery
import dev.codeswamp.articlecommand.application.usecase.query.article.versionedarticle.GetVersionedArticleUseCase
import dev.codeswamp.articlecommand.application.usecase.query.article.versionedarticle.ReadArticleResult
import org.springframework.stereotype.Service

@Service
class ArticleQueryUseCaseFacade(
    private val getVersionedArticleUseCase: GetVersionedArticleUseCase,
    private val getDraftsUseCase: GetDraftsUseCase
) {
    suspend fun getVersionedArticle(query: GetVersionedArticleQuery): ReadArticleResult {
        return getVersionedArticleUseCase.handle(query)
    }

    suspend fun getDraftsOfUser(query : GetDraftsQuery) : DraftList {
        return getDraftsUseCase.handle(query)
    }
}