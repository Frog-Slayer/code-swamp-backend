package dev.codeswamp.core.article.application.service.impl

import dev.codeswamp.core.article.application.dto.command.ArticleWriteCommand
import dev.codeswamp.core.article.application.service.ArticleCommandService
import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.service.ArticleService
import dev.codeswamp.core.article.presentation.dto.request.ArticleMetadataDto
import org.springframework.stereotype.Service

@Service
class ArticleCommandServiceImpl(
    private val articleService: ArticleService,
) : ArticleCommandService {

    override fun create(articleWriteCommand: ArticleWriteCommand) {

        articleService.create(Article(
            title = articleWriteCommand.title,
            authorId = articleWriteCommand.userId,
            folderId = articleWriteCommand.folderId,
            isPublic = articleWriteCommand.isPublic,
            content = articleWriteCommand.content,
            summary = articleWriteCommand.summary,
            thumbnailUrl = articleWriteCommand.thumbnailUrl,
            slug = articleWriteCommand.slug,
        ))
    }

    override fun updateArticleMetadata(
        userId: Long,
        articleId: Long,
        metadata: ArticleMetadataDto
    ) {
        TODO("Not yet implemented")
    }

    override fun updateArticleContent(userId: Long, articleId: Long, content: String) {
        TODO("Not yet implemented")
    }

    override fun delete(userId: Long, articleId: Long) {
        TODO("Not yet implemented")
    }
}