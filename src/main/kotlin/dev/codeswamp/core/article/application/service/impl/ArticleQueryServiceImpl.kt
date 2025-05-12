package dev.codeswamp.core.article.application.service.impl

import dev.codeswamp.core.article.application.service.ArticleQueryService
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto

class ArticleQueryServiceImpl : ArticleQueryService {
    override fun findByArticleId(
        userId: Long?,
        articleId: Long
    ): ArticleReadResponseDto {
        TODO("Not yet implemented")
    }

    override fun findAllByKeywords(
        userId: Long?,
        keywords: List<String>
    ): List<ArticleReadResponseDto> {
        TODO("Not yet implemented")
    }
}