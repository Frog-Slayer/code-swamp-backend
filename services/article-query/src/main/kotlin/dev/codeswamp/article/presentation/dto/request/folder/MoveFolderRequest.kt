package dev.codeswamp.article.presentation.dto.request.folder

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import dev.codeswamp.article.presentation.json.StringToLongDeserializer

data class MoveFolderRequest(
    @JsonDeserialize(using = StringToLongDeserializer::class)
    val newParentId: Long
)