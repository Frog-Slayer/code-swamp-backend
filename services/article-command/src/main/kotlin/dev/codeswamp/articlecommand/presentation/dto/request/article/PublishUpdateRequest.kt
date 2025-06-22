package dev.codeswamp.articlecommand.presentation.dto.request.article

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import dev.codeswamp.articlecommand.application.usecase.command.article.publish.UpdatePublishCommand
import dev.codeswamp.articlecommand.presentation.json.StringToLongDeserializer

data class PublishUpdateRequest(
    val title: String,

    @JsonDeserialize(using = StringToLongDeserializer::class)
    val versionId: Long,
    val diff: String,
    val isPublic: Boolean,
    val thumbnailUrl: String?,
    val slug: String,
    val summary: String,

    @JsonDeserialize(using = StringToLongDeserializer::class)
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