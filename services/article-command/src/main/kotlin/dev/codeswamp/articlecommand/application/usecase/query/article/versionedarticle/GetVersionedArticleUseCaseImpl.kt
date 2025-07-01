package dev.codeswamp.articlecommand.application.usecase.query.article.versionedarticle

import dev.codeswamp.articlecommand.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import dev.codeswamp.articlecommand.domain.support.DiffProcessor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetVersionedArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val diffProcessor: DiffProcessor
) : GetVersionedArticleUseCase {

    @Transactional(readOnly = true)
    override suspend fun handle(query: GetVersionedArticleQuery): ReadArticleResult {
        val article = articleRepository.findById(query.articleId)
            ?: throw ArticleNotFoundException("해당 버전의 글을 찾을 수 없습니다")

        val fullContent = article.apply{ checkOwnership(query.userId ) }
                            .restoreFullContent(query.versionId, diffProcessor)

        val version = article.getVersion(query.versionId)

        return ReadArticleResult(
            id = article.id,
            authorId = article.authorId,
            createdAt = article.createdAt,
            updatedAt = version.createdAt,
            folderId = article.metadata.folderId,
            summary = article.metadata.summary,
            thumbnailUrl = article.metadata.thumbnailUrl,
            isPublic = article.metadata.isPublic,
            title = version.title?.value ?: "",
            content = fullContent,
        )
    }

}