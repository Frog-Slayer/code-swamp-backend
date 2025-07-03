package dev.codeswamp.articlequery.application.usecase.query.article.read.byslug

import dev.codeswamp.articlequery.application.usecase.query.article.read.ReadArticleResult

interface GetPublishedArticleBySlugUseCase {
    suspend fun handle(query: GetPublishedArticleBySlugQuery): ReadArticleResult
}