package dev.codeswamp.articlecommand.application.usecase.command.article.delete

import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import dev.codeswamp.core.application.event.EventRecorder
import org.springframework.stereotype.Service

@Service
class DeleteArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val eventRecorder: EventRecorder,
) : DeleteArticleUseCase {

    override suspend fun delete(command: DeleteArticleCommand) {



    }

}