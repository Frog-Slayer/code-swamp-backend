package dev.codeswamp.core.article.application.usecase.query.read.byslug

data class GetPublishedArticleBySlugQuery (
    val userId : Long?,
    val path : String,
)