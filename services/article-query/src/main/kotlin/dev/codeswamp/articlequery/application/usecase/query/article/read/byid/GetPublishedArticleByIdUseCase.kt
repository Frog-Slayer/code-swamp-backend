package dev.codeswamp.articlequery.application.usecase.query.article.read.byid

import dev.codeswamp.articlequery.application.usecase.query.article.read.ReadArticleResult

interface GetPublishedArticleByIdUseCase {
    suspend fun handle(query: GetPublishedArticleByIdQuery): ReadArticleResult
}