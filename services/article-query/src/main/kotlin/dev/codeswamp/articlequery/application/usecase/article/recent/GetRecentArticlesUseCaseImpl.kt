package dev.codeswamp.articlequery.application.usecase.article.recent

import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle
import dev.codeswamp.articlequery.application.readmodel.repository.PublishedArticleRepository
import org.springframework.stereotype.Service

@Service
class GetRecentArticlesUseCaseImpl(
    private val articleRepository: PublishedArticleRepository,
) : GetRecentArticlesUseCase {
   override suspend fun handle(query: GetRecentArticlesQuery) : List<PublishedArticle> {
       val (userId, lastCreatedAt, lastArticleId, limit, fields) = query

        return articleRepository.findRecentArticles(userId, lastCreatedAt, lastArticleId, limit, fields)
    }
}