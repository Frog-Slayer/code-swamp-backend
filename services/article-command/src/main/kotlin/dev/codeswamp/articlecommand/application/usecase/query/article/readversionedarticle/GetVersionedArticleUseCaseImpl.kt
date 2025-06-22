package dev.codeswamp.articlecommand.application.usecase.query.article.readversionedarticle

import dev.codeswamp.articlecommand.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import dev.codeswamp.articlecommand.domain.article.service.ArticleContentReconstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetVersionedArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val contentReconstructor: ArticleContentReconstructor,
) : GetVersionedArticleUseCase {

    @Transactional(readOnly = true)
    override suspend fun handle(query: GetVersionedArticleQuery): ReadArticleResult {
        val article = articleRepository.findByIdAndVersionId(query.articleId, query.versionId)
            ?: throw ArticleNotFoundException("해당 버전의 글을 찾을 수 없습니다")

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

}