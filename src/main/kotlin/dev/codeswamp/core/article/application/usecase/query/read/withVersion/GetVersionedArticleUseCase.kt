package dev.codeswamp.core.article.application.usecase.query.read.withVersion

import dev.codeswamp.core.article.application.usecase.query.read.ReadArticleResult

interface GetVersionedArticleUseCase {
    fun handle(query: GetVersionedArticleQuery) : ReadArticleResult
}