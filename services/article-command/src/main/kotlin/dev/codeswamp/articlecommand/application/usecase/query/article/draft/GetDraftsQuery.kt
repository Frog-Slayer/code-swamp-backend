package dev.codeswamp.articlecommand.application.usecase.query.article.draft

data class GetDraftsQuery(
    val userId: Long,
    val articleId: Long,
)