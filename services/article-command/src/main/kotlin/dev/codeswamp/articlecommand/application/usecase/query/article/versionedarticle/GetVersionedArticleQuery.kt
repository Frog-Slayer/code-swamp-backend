package dev.codeswamp.articlecommand.application.usecase.query.article.versionedarticle

data class GetVersionedArticleQuery(
    val userId: Long,//should be private
    val articleId: Long,
    val versionId: Long
)