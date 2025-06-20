package dev.codeswamp.core.article.application.usecase.query.article.read.withversion

import dev.codeswamp.core.article.application.cache.FolderDeletionCache
import dev.codeswamp.core.article.application.exception.article.ArticleNotFoundException
import dev.codeswamp.core.article.application.exception.folder.FolderNotFoundException
import dev.codeswamp.core.article.application.usecase.query.article.read.ReadArticleResult
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.domain.article.service.ArticleContentReconstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetVersionedArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val contentReconstructor: ArticleContentReconstructor,
    private val deletionCache: FolderDeletionCache
): GetVersionedArticleUseCase {

    @Transactional(readOnly = true)
    override fun handle(query: GetVersionedArticleQuery): ReadArticleResult {
        val article = articleRepository.findByIdAndVersionId(query.articleId, query.versionId)
                        ?: throw ArticleNotFoundException("해당 버전의 글을 찾을 수 없습니다")

        checkFolderDeletionCache(article.metadata.folderId)

        article.checkOwnership(query.userId)
        val fullContent = contentReconstructor.reconstructFullContent(article.currentVersion)

        return ReadArticleResult(
            id = article.id,
            authorId = article.authorId,
            createdAt = article.createdAt,
            updatedAt = article.currentVersion.createdAt,
            folderId = article.metadata.folderId,
            summary = article.metadata.summary,
            thumbnailUrl = article.metadata.thumbnailUrl,
            isPublic = article.metadata.isPublic,
            title = article.currentVersion.title?.value ?: "",
            content = fullContent,
        )
    }

    fun checkFolderDeletionCache(folderId: Long) {
        if (deletionCache.isDeleted(folderId)) throw FolderNotFoundException.byId(folderId)
    }
}