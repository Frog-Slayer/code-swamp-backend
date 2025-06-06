package dev.codeswamp.core.article.application.usecase.query.read.byid

import dev.codeswamp.core.article.application.usecase.query.read.ReadArticleResult

interface GetPublishedArticleByIdUseCase {
    fun handle(query: GetPublishedArticleByIdQuery) : ReadArticleResult
}