package dev.codeswamp.articlequery.presentation.dto.request.folder

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import dev.codeswamp.articlequery.presentation.json.StringToLongDeserializer

data class CreateFolderRequest(
    @JsonDeserialize(using = StringToLongDeserializer::class)
    val parentId: Long,
    val name: String
)