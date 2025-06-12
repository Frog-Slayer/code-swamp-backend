package dev.codeswamp.core.article.application.usecase.query.article.read.byid

data class GetPublishedArticleByIdQuery (
    val userId: Long?,
    val articleId: Long
)