package dev.codeswamp.core.article.application.service

import dev.codeswamp.core.article.application.dto.command.ArticleWriteCommand
import dev.codeswamp.core.article.presentation.dto.request.ArticleMetadataDto

interface ArticleCommandService {
    fun create(articleWriteCommand: ArticleWriteCommand)

    fun updateArticleMetadata(userId: Long, articleId: Long, metadata: ArticleMetadataDto)

    fun updateArticleContent(userId: Long, articleId: Long, content: String)

    fun delete(userId: Long, articleId: Long)
}