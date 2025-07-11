package dev.codeswamp.articlequery.application.usecase.folder.byids

import dev.codeswamp.databasequery.FieldSelection
import java.time.Instant

data class GetAllFoldersByIdsQuery(
    val folderIds: List<Long>,
    val fields: Set<String>
)