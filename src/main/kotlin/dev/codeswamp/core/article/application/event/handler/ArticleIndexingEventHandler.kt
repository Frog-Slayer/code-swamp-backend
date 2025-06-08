package dev.codeswamp.core.article.application.event.handler

import dev.codeswamp.core.article.application.dto.command.ArticleIndexDTO
import dev.codeswamp.core.article.application.event.event.ArticleIndexingEvent
import dev.codeswamp.core.article.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.core.article.application.support.ArticleSearchIndexer
import dev.codeswamp.core.article.application.support.MarkdownPreprocessor
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class ArticleIndexingEventHandler(
    private val publishedArticleRepository: PublishedArticleRepository,
    private val articleSearchIndexer: ArticleSearchIndexer,
    private val markdownPreprocessor: MarkdownPreprocessor
) {

    @Async//TODO
    @EventListener
    fun handle(event: ArticleIndexingEvent ) {
        val articleToIndex = requireNotNull(publishedArticleRepository.findByArticleId(event.articleId))
        val preprocessedText = markdownPreprocessor.preprocess(articleToIndex.content)

        articleSearchIndexer.index(ArticleIndexDTO(
            articleId = articleToIndex.id,
            authorId = articleToIndex.authorId,
            title = articleToIndex.title,
            preprocessedText = preprocessedText,
            isPublic = articleToIndex.isPublic,
        ))
    }
}
