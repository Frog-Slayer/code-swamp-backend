package dev.codeswamp.core.article.presentation.dto.request

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import dev.codeswamp.core.article.application.usecase.command.draft.CreateDraftCommand
import dev.codeswamp.core.article.presentation.json.StringToLongDeserializer

data class DraftRequest (
    val title: String,
    val diff: String,

    @JsonDeserialize(using = StringToLongDeserializer::class)
    val folderId: Long,
) {
    fun toCommand(userId: Long?) = CreateDraftCommand(
        userId = requireNotNull(userId),
        title = title,
        diff = diff,
        folderId = folderId
    )
}