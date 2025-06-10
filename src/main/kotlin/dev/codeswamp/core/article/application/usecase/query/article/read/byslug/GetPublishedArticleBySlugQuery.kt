package dev.codeswamp.core.article.application.usecase.query.article.read.byslug

data class GetPublishedArticleBySlugQuery (
    val userId : Long?,
    val path : String,
)