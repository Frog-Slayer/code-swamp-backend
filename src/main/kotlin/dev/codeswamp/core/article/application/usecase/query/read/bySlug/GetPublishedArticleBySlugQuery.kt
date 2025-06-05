package dev.codeswamp.core.article.application.usecase.query.read.bySlug

data class GetPublishedArticleBySlugQuery (
    val userId : Long?,
    val path : String,
)