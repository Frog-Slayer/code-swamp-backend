package dev.codeswamp.core.article.application.usecase.query.article.read.byslug

import dev.codeswamp.core.article.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.core.article.application.usecase.query.article.read.ReadArticleResult
import dev.codeswamp.core.article.application.exception.article.ArticleNotFoundException
import dev.codeswamp.core.article.application.exception.folder.FolderNotFoundException
import dev.codeswamp.core.article.domain.folder.repository.FolderRepository
import org.springframework.stereotype.Service

@Service
class GetPublishedArticleBySlugUseCaseImpl(
    private val folderRepository: FolderRepository,
    private val publishedArticleRepository: PublishedArticleRepository,
): GetPublishedArticleBySlugUseCase {
    override fun handle(query: GetPublishedArticleBySlugQuery): ReadArticleResult {
        val fullPath = query.path.split("/")
        val folderPath = fullPath.dropLast(1).joinToString("/")
        val slug = fullPath.last()

        val folderId = folderRepository.findFolderByFolderPath(folderPath)?.id
            ?: throw FolderNotFoundException.byPath(query.path)

        val article = publishedArticleRepository.findByFolderIdAndSlug(folderId, slug)
            ?: throw ArticleNotFoundException.bySlug(slug)

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
}