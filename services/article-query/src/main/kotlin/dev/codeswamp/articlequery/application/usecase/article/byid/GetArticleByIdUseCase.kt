package dev.codeswamp.articlequery.application.usecase.article.byid

import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle

interface GetArticleByIdUseCase {
    suspend fun handle(query: GetArticleByIdQuery) : PublishedArticle
}