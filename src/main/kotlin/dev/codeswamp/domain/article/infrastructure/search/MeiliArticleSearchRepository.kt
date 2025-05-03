package dev.codeswamp.domain.article.infrastructure.search

import org.springframework.stereotype.Repository

@Repository
class MeiliArticleSearchRepository : ArticleSearchRepository {
    override fun searchArticleIdsByKeywords(keywords: List<String>): List<Long> {
        TODO("Not yet implemented")
    }
}