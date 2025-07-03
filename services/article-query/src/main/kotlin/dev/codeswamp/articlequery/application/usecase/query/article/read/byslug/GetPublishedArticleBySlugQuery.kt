package dev.codeswamp.articlequery.application.usecase.query.article.read.byslug

data class GetPublishedArticleBySlugQuery(
    val userId: Long?,
    val path: String,
)