package dev.codeswamp.articlequery.application.usecase.query.article.status

data class CheckVersionExistsQuery (
    val articleId: Long,
    val versionId : Long
)