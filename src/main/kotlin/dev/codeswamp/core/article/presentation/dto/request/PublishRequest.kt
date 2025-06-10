package dev.codeswamp.core.article.presentation.dto.request

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import dev.codeswamp.core.article.application.usecase.command.article.publish.CreatePublishCommand
import dev.codeswamp.core.article.presentation.json.StringToLongDeserializer

data class PublishRequest (
    val title: String,
    val diff: String,
    val isPublic: Boolean,
    val thumbnailUrl: String?,
    val slug: String,
    val summary: String,

    @JsonDeserialize(using = StringToLongDeserializer::class)
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