package dev.codeswamp.articlequery.application.usecase.article.byauthor

import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle
import dev.codeswamp.articlequery.application.readmodel.repository.PublishedArticleRepository
import org.springframework.stereotype.Service

@Service
class GetArticlesOfAuthorUseCaseImpl(
    private val articleRepository: PublishedArticleRepository,
) : GetArticlesOfAuthorUseCase {
    override suspend fun handle(query: GetArticlesByAuthorIdQuery): List<PublishedArticle> {
        val articleFields = query.fields

        return articleRepository.findAllByAuthorId(query.userId, query.authorId,  articleFields)
    }
}