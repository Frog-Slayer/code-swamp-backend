package dev.codeswamp.articlecommand.application.usecase.command.article.delete

import dev.codeswamp.articlecommand.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import dev.codeswamp.core.application.event.EventRecorder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val eventRecorder: EventRecorder,
) : DeleteArticleUseCase {

    @Transactional
    override suspend fun delete(command: DeleteArticleCommand) {
        val articleId = command.articleId
        val article = articleRepository.findById(articleId)
            ?: throw ArticleNotFoundException.byId(articleId)

        val deleted = article
            .apply { checkOwnership(command.userId) }
            .delete()

        eventRecorder.recordAll(deleted.pullEvents())

        articleRepository.deleteById(articleId)
    }
}