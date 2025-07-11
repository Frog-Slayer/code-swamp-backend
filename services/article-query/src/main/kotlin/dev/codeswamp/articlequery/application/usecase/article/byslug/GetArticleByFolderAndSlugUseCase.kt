package dev.codeswamp.articlequery.application.usecase.article.byslug

import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle

interface GetArticleByFolderAndSlugUseCase {
    suspend fun handle(query: GetArticleByPathAndSlugQuery) : PublishedArticle
}