package dev.codeswamp.core.article.application.usecase.query.read.byslug

import dev.codeswamp.core.article.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.core.article.application.usecase.query.read.ReadArticleResult
import dev.codeswamp.core.article.application.exception.article.ArticleNotFoundException
import dev.codeswamp.core.article.application.exception.folder.FolderPathNotFoundException
import dev.codeswamp.core.article.domain.folder.service.FolderPathResolver
import org.springframework.stereotype.Service

@Service
class GetPublishedArticleBySlugUseCaseImpl(
    private val folderPathResolver: FolderPathResolver,
    private val publishedArticleRepository: PublishedArticleRepository,
): GetPublishedArticleBySlugUseCase {
    override fun handle(query: GetPublishedArticleBySlugQuery): ReadArticleResult {
        val path = query.path.split("/")
        val slug = path.last()

        val folderId = folderPathResolver.findFolderByFullPath(path.dropLast(1))?.id
            ?: throw FolderPathNotFoundException(query.path)

        val article = publishedArticleRepository.findByFolderIdAndSlug(folderId, slug)
            ?: throw ArticleNotFoundException.bySlug("slug")

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