package dev.codeswamp.core.article.presentation.dto.request

import dev.codeswamp.core.article.application.usecase.command.draft.CreateDraftCommand

data class DraftRequest (
    val title: String,
    val diff: String,
    val folderId: Long,
) {
    fun toCommand(userId: Long?) = CreateDraftCommand(
        userId = requireNotNull(userId),
        title = title,
        diff = diff,
        folderId = folderId
    )
}