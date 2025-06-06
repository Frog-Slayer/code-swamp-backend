package dev.codeswamp.core.article.application.usecase.query.read.withversion

import dev.codeswamp.core.article.application.usecase.query.read.ReadArticleResult

interface GetVersionedArticleUseCase {
    fun handle(query: GetVersionedArticleQuery) : ReadArticleResult
}