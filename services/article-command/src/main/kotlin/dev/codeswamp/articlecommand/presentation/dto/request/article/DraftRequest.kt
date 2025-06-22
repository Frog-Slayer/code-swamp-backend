package dev.codeswamp.articlecommand.presentation.dto.request.article

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import dev.codeswamp.articlecommand.application.usecase.command.article.draft.CreateDraftCommand
import dev.codeswamp.articlecommand.presentation.json.StringToLongDeserializer

data class DraftRequest(
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