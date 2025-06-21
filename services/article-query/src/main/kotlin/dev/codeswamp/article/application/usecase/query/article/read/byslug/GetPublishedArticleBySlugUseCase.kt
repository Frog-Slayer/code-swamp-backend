package dev.codeswamp.article.application.usecase.query.article.read.byslug

import dev.codeswamp.article.application.usecase.query.article.read.ReadArticleResult

interface GetPublishedArticleBySlugUseCase {
    fun handle(query: GetPublishedArticleBySlugQuery): ReadArticleResult
}