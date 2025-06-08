package dev.codeswamp.core.article.application.usecase.query.read.byslug

import dev.codeswamp.core.article.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.core.article.application.usecase.query.read.ReadArticleResult
import dev.codeswamp.core.article.domain.article.exception.article.ArticleNotFoundException
import dev.codeswamp.core.article.domain.folder.service.FolderPathResolver
import org.springframework.stereotype.Service
import java.awt.geom.IllegalPathStateException

@Service
class GetPublishedArticleBySlugUseCaseImpl(
    private val folderPathResolver: FolderPathResolver,
    private val publishedArticleRepository: PublishedArticleRepository,
): GetPublishedArticleBySlugUseCase {
    override fun handle(query: GetPublishedArticleBySlugQuery): ReadArticleResult {
        val path = query.path.split("/")
        val slug = path.last()

        val folderId = folderPathResolver.findFolderByFullPath(path.dropLast(1))?.id
            ?: throw IllegalPathStateException("잘못된 URI입니다.")

        val article = publishedArticleRepository.findByFolderIdAndSlug(folderId, slug)
            ?: throw ArticleNotFoundException("해당 경로의 글을 찾을 수 없습니다.")

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