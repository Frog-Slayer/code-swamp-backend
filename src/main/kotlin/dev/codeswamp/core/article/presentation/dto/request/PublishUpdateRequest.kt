package dev.codeswamp.core.article.presentation.dto.request

import dev.codeswamp.core.article.application.usecase.command.publish.UpdatePublishCommand

data class PublishUpdateRequest (
    val title: String,
    val versionId: Long,
    val diff: String,
    val type: String,
    val isPublic: Boolean,
    val thumbnailUrl: String?,
    val slug: String,
    val summary: String,
    val folderId: Long,
) {
    fun toCommand(userId: Long?, articleId: Long) = UpdatePublishCommand(
        userId = requireNotNull(userId),
        articleId = articleId,
        versionId = versionId,

        title = title,
        diff = diff,
        folderId = folderId,
        isPublic = isPublic,
        thumbnailUrl = thumbnailUrl,
        slug = slug,
        summary = summary,
    )
}