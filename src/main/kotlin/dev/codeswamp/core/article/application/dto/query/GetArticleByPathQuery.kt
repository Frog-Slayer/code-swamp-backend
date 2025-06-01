package dev.codeswamp.core.article.application.dto.query

data class GetArticleByPathQuery (
    val userId : Long?,
    val path : String,
)