package dev.codeswamp.articlecommand.application.usecase.query.article.readversionedarticle

interface GetVersionedArticleUseCase {
    suspend fun handle(query: GetVersionedArticleQuery): ReadArticleResult
}