package dev.codeswamp.articlequery.application.usecase.article.byauthor

import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle

interface GetArticlesOfAuthorUseCase {
    suspend fun handle(query: GetArticlesByAuthorIdQuery) : List<PublishedArticle>
}