package dev.codeswamp.article.application.usecase.query.article.read.withversion

import dev.codeswamp.article.application.usecase.query.article.read.ReadArticleResult

interface GetVersionedArticleUseCase {
    fun handle(query: GetVersionedArticleQuery) : ReadArticleResult
}