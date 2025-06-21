package dev.codeswamp.article.application.usecase.query.article.read.withversion

data class GetVersionedArticleQuery(
    val userId: Long,//should be private
    val articleId: Long,
    val versionId: Long
)