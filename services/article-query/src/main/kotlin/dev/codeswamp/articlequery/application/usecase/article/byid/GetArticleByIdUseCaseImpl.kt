package dev.codeswamp.articlequery.application.usecase.article.byid

import dev.codeswamp.articlequery.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle
import dev.codeswamp.articlequery.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.databasequery.FieldSelection
import org.springframework.stereotype.Service

@Service
class GetArticleByIdUseCaseImpl(
    private val articleRepository: PublishedArticleRepository,
) : GetArticleByIdUseCase {

    override suspend fun handle(query: GetArticleByIdQuery) : PublishedArticle {
        val articleFields = query.fields

        return articleRepository.findByArticleId(query.userId, query.articleId, articleFields)
            ?: throw ArticleNotFoundException.Companion.byId(query.articleId)
    }
}