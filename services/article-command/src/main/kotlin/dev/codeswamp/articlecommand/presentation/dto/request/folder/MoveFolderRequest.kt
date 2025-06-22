package dev.codeswamp.articlecommand.presentation.dto.request.folder

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import dev.codeswamp.articlecommand.presentation.json.StringToLongDeserializer

data class MoveFolderRequest(
    @JsonDeserialize(using = StringToLongDeserializer::class)
    val newParentId: Long
)