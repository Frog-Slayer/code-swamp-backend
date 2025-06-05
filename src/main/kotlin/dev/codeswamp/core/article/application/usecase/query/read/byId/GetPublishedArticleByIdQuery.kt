package dev.codeswamp.core.article.application.usecase.query.read.byId

data class GetPublishedArticleByIdQuery (
    val userId: Long?,
    val articleId: Long
)