package dev.codeswamp.core.article.presentation.dto.request

data class ArticleWriteRequestDto (
    val title: String,
    val content: String,
    //val slug: String, -> TODO
    val isPublic: Boolean,
    val folderId: Long? = null
)