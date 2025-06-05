package dev.codeswamp.core.article.application.usecase.impl

import dev.codeswamp.core.article.application.dto.command.ArticleWriteCommand
import dev.codeswamp.core.article.application.dto.command.UpdateArticleCommand
import dev.codeswamp.core.article.application.usecase.ArticleCommandUseCase
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.service.ArticleDomainService
import org.springframework.stereotype.Service

@Service
class ArticleCommandUseCaseImpl() : ArticleCommandUseCase {

    override fun create(articleWriteCommand: ArticleWriteCommand) {

        articleDomainService.create(VersionedArticle(
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

    override fun update(updateArticleCommand: UpdateArticleCommand) {
        TODO("Not yet implemented")
    }


    override fun delete(userId: Long, articleId: Long) {
        TODO("Not yet implemented")
    }
}