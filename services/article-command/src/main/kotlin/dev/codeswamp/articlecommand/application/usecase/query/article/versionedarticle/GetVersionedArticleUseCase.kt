package dev.codeswamp.articlecommand.application.usecase.query.article.versionedarticle

interface GetVersionedArticleUseCase {
    suspend fun handle(query: GetVersionedArticleQuery): ReadArticleResult
}