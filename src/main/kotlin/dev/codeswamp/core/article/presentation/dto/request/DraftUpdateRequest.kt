package dev.codeswamp.core.article.presentation.dto.request

import dev.codeswamp.core.article.application.usecase.command.draft.UpdateDraftCommand

data class DraftUpdateRequest (
    val title: String,
    val diff: String,
    val folderId: Long,
) {
    fun toCommand(userId: Long?, articleId: Long, versionId : Long) = UpdateDraftCommand(
        userId = requireNotNull(userId),
        articleId = articleId,
        versionId = versionId,
        title = title,
        diff = diff,
        folderId = folderId,
    )
}