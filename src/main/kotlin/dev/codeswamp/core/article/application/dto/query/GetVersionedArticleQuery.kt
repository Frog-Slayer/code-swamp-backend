package dev.codeswamp.core.article.application.dto.query

data class GetVersionedArticleQuery(
    val userId: Long,
    val articleId: Long,
    val versionId: Long,
)