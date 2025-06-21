package dev.codeswamp.articlecommand.presentation.dto.response.article

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import dev.codeswamp.articlecommand.application.usecase.command.article.draft.DraftArticleResult

data class DraftResponse(
    @JsonSerialize(using = ToStringSerializer::class)
    val articleId: Long,

    @JsonSerialize(using = ToStringSerializer::class)
    val versionId: Long
) {
    companion object {
        fun from(draftArticleResult: DraftArticleResult) = DraftResponse(
            articleId = draftArticleResult.articleId,
            versionId = draftArticleResult.versionId
        )
    }
}