package dev.codeswamp.core.article.application.dto.command

data class ArticleWriteCommand (
    val userId: Long,
    val title: String,
    val content: String,
    val isPublic: Boolean,
    //val folderId: Long? = null
    //val slug: String, TODO
)
