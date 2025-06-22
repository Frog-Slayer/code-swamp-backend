package dev.codeswamp.article.presentation.dto.request.article

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import dev.codeswamp.article.application.usecase.command.article.draft.UpdateDraftCommand
import dev.codeswamp.article.presentation.json.StringToLongDeserializer

data class DraftUpdateRequest(
    val title: String,
    val diff: String,

    @JsonDeserialize(using = StringToLongDeserializer::class)
    val folderId: Long,
) {
    fun toCommand(userId: Long?, articleId: Long, versionId: Long) = UpdateDraftCommand(
        userId = requireNotNull(userId),
        articleId = articleId,
        versionId = versionId,
        title = title,
        diff = diff,
        folderId = folderId,
    )
}