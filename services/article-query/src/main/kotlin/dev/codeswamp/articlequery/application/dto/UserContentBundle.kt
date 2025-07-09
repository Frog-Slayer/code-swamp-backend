package dev.codeswamp.articlequery.application.dto

import dev.codeswamp.articlequery.application.readmodel.model.Folder
import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle
import dev.codeswamp.articlequery.application.readmodel.model.UserProfile
import java.time.Instant

data class UserContentBundle (
    val folders: List<Folder>,
    val articles: List<EnrichedArticle>
)
