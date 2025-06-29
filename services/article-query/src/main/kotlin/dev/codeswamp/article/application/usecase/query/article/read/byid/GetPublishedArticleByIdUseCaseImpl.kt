package dev.codeswamp.article.application.usecase.query.article.read.byid

import dev.codeswamp.article.application.cache.FolderDeletionCache
import dev.codeswamp.article.application.exception.article.ArticleNotFoundException
import dev.codeswamp.article.application.exception.folder.FolderNotFoundException
import dev.codeswamp.article.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.article.application.usecase.query.article.read.ReadArticleResult
import org.springframework.stereotype.Service

@Service
class GetPublishedArticleByIdUseCaseImpl(
    private val publishedArticleRepository: PublishedArticleRepository,
    private val deletionCache: FolderDeletionCache
) : GetPublishedArticleByIdUseCase {
    override fun handle(query: GetPublishedArticleByIdQuery): ReadArticleResult {
        val article = publishedArticleRepository.findByArticleId(query.articleId)
            ?: throw ArticleNotFoundException.Companion.byId(query.articleId)

        checkFolderDeletionCache(article.folderId)

        article.assertReadableBy(query.userId)

        return ReadArticleResult(
            id = article.id,
            authorId = article.authorId,
            createdAt = article.createdAt,
            updatedAt = article.createdAt,
            folderId = article.folderId,
            summary = article.summary,
            thumbnailUrl = article.thumbnailUrl,
            isPublic = article.isPublic,
            title = article.title,
            content = article.content,
        )
    }

    fun checkFolderDeletionCache(folderId: Long) {
        if (deletionCache.isDeleted(folderId)) throw FolderNotFoundException.Companion.byId(folderId)
    }
}