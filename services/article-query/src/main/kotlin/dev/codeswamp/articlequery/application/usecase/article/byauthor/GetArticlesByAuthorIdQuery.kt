package dev.codeswamp.articlequery.application.usecase.article.byauthor

data class GetArticlesByAuthorIdQuery(
    val userId: Long?,
    val authorId: Long,
    val fields: Set<String>
)