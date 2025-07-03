package dev.codeswamp.articlequery.presentation.dto.request.folder

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import dev.codeswamp.articlequery.presentation.json.StringToLongDeserializer

data class MoveFolderRequest(
    @JsonDeserialize(using = StringToLongDeserializer::class)
    val newParentId: Long
)