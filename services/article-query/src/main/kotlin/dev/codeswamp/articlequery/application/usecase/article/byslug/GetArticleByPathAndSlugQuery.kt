package dev.codeswamp.articlequery.application.usecase.article.byslug

import dev.codeswamp.databasequery.FieldSelection

data class GetArticleByPathAndSlugQuery(
    val userId: Long?,
    val folderId: Long,
    val slug: String,
    val articleFields: Set<String>,
    val folderFields: Set<String>
)