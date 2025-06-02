package dev.codeswamp.core.article.application.dto.query

data class GetArticleByIdQuery (
    val userId: Long?,
    val articleId: Long
)