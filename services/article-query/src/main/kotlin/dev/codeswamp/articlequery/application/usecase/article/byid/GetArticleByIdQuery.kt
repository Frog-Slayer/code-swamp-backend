package dev.codeswamp.articlequery.application.usecase.article.byid

data class GetArticleByIdQuery(
    val userId: Long?,
    val articleId: Long,
    val fields: Set<String>
)