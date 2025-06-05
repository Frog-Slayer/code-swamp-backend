package dev.codeswamp.core.article.application.usecase.query.read.withVersion

data class GetVersionedArticleQuery (
    val userId: Long,//should be private
    val articleId: Long,
    val versionId: Long
)