package dev.codeswamp.article.presentation.dto.request.folder

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import dev.codeswamp.article.presentation.json.StringToLongDeserializer

data class CreateFolderRequest(
    @JsonDeserialize(using = StringToLongDeserializer::class)
    val parentId: Long,
    val name: String
)