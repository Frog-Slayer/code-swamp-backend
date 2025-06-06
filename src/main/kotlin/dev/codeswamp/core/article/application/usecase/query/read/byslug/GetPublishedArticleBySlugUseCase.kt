package dev.codeswamp.core.article.application.usecase.query.read.byslug

import dev.codeswamp.core.article.application.usecase.query.read.ReadArticleResult

interface GetPublishedArticleBySlugUseCase {
    fun handle(query: GetPublishedArticleBySlugQuery) : ReadArticleResult
}