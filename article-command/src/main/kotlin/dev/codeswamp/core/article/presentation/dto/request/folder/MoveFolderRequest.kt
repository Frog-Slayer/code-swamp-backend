package dev.codeswamp.core.article.presentation.dto.request.folder

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import dev.codeswamp.core.article.presentation.json.StringToLongDeserializer

data class MoveFolderRequest (
    @JsonDeserialize(using = StringToLongDeserializer::class)
    val newParentId: Long
)