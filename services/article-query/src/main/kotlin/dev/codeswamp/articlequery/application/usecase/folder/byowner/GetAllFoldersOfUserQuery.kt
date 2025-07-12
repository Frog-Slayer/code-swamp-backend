package dev.codeswamp.articlequery.application.usecase.folder.byowner

import dev.codeswamp.databasequery.FieldSelection

data class GetAllFoldersOfUserQuery(
    val ownerId: Long,
    val fields: Set<String>
)