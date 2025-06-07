package dev.codeswamp.core.article.application.handler

import dev.codeswamp.core.article.application.events.ArticleIndexingEvent
import dev.codeswamp.core.article.application.readmodel.model.PublishedArticle
import dev.codeswamp.core.article.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.core.article.domain.article.events.ArticlePublishedEvent
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.domain.article.service.ArticleContentReconstructor
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ArticlePublishedEventHandler(
    private val articleRepository: ArticleRepository,
    private val publishedArticleRepository: PublishedArticleRepository,
    private val reconstructor: ArticleContentReconstructor,
    private val eventPublisher: ApplicationEventPublisher
) {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)//TODO: Race Condition 있음
    fun handle(event: ArticlePublishedEvent) {
        //이전 버전, 이전 published본 archiving
        val previousPublished = articleRepository.findPreviousPublishedVersion(event.articleId, event.versionId)
        val previousVersion = event.previousVersionId?.let { articleRepository.findVersionByVersionId(it) }

        previousPublished?.archive()?.let { articleRepository.saveVersion(it) }
        previousVersion?.archive()?.let { articleRepository.saveVersion(it) }

        //read model 저장.
        val articleToPublish = articleRepository.findByIdAndVersionId(event.articleId, event.versionId)
            ?.let {
                PublishedArticle.of(
                    it,
                    reconstructor.reconstructFullContent(it.currentVersion)
                )
            }
            ?: throw IllegalStateException("cannot publish this article")

        publishedArticleRepository.save(articleToPublish)

        eventPublisher.publishEvent(ArticleIndexingEvent(articleToPublish.id))
    }
}
