package dev.codeswamp.article.application.usecase.query.article.read.byid

import dev.codeswamp.article.application.usecase.query.article.read.ReadArticleResult

interface GetPublishedArticleByIdUseCase {
    fun handle(query: GetPublishedArticleByIdQuery) : ReadArticleResult
}