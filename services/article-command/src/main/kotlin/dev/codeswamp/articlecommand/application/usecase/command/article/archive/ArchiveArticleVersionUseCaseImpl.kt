package dev.codeswamp.articlecommand.application.usecase.command.article.archive

import dev.codeswamp.articlecommand.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import dev.codeswamp.framework.application.outbox.EventRecorder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArchiveArticleVersionUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val eventRecorder: EventRecorder,
) : ArchiveArticleVersionUseCase {

    @Transactional
    override suspend fun delete(command: ArchiveArticleVersionCommand) {
        val articleId = command.articleId
        val article = articleRepository.findById(articleId)
            ?: throw ArticleNotFoundException.byId(articleId)

        val archived = article
            .apply { checkOwnership(command.userId) }
            .archive(command.versionId)
            .also {
                articleRepository.updateVersions(listOf(it.getVersion(versionId = command.versionId)))
            }

        eventRecorder.recordAll(archived.pullEvents())
    }
}