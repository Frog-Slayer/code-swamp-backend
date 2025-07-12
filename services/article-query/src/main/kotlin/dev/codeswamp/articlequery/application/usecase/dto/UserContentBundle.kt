package dev.codeswamp.articlequery.application.usecase.dto

import dev.codeswamp.articlequery.application.readmodel.model.Folder

data class UserContentBundle (
    val folders: List<Folder>,
    val articles: List<EnrichedArticle>
)
