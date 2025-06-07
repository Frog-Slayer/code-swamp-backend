package dev.codeswamp.core.article.presentation.dto.request

import dev.codeswamp.core.article.application.usecase.command.publish.CreatePublishCommand

data class PublishRequest (
    val title: String,
    val diff: String,
    val type: String,
    val isPublic: Boolean,
    val thumbnailUrl: String?,
    val slug: String,
    val summary: String,
    val folderId: Long,
) {
    fun toCommand(userId: Long?) = CreatePublishCommand(
        userId = requireNotNull(userId),
        title = title,
        diff = diff,
        folderId = folderId,
        isPublic = isPublic,
        thumbnailUrl = thumbnailUrl,
        slug = slug,
        summary = summary,
    )
}