package dev.codeswamp.core.article.application.usecase

import dev.codeswamp.core.article.application.dto.command.ArticleWriteCommand
import dev.codeswamp.core.article.application.dto.command.UpdateArticleCommand

interface ArticleCommandUseCase {
    fun create(articleWriteCommand: ArticleWriteCommand)

    fun update(updateArticleCommand: UpdateArticleCommand)

    fun delete(userId: Long, articleId: Long)
}