package dev.codeswamp.core.article.presentation.dto.response

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import dev.codeswamp.core.article.application.usecase.command.draft.DraftArticleResult

data class DraftResponse (
    @JsonSerialize(using = ToStringSerializer::class)
    val articleId : Long,

    @JsonSerialize(using = ToStringSerializer::class)
    val versionId : Long
){
    companion object {
        fun from(draftArticleResult: DraftArticleResult) = DraftResponse(
            articleId = draftArticleResult.articleId,
            versionId = draftArticleResult.versionId
        )
    }
}