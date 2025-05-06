package dev.codeswamp.core.article.application.service

import dev.codeswamp.core.article.domain.service.ArticleService
import dev.codeswamp.core.article.infrastructure.search.ArticleSearchRepository
import dev.codeswamp.core.article.presentation.dto.request.ArticleMetadataDto
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto
import dev.codeswamp.core.user.entity.User
import org.springframework.stereotype.Service

@Service
class ArticleApplicationServiceImpl(
    private val articleService: ArticleService,
    private val articleSearchRepository: ArticleSearchRepository,
    ) : ArticleApplicationService {

    override fun create(
        user: User,
        metadata: ArticleMetadataDto,
        content: String
    ) {
        TODO("Not yet implemented")
    }

    override fun updateArticleMetadata(
        user: User,
        articleId: Long,
        metadata: ArticleMetadataDto
    ) {
        TODO("Not yet implemented")
    }

    override fun updateArticleContent(user: User, articleId: Long, content: String) {
        TODO("Not yet implemented")
    }

    override fun delete(user: User, articleId: Long) {
        TODO("Not yet implemented")
    }

    override fun findByArticleId(
        user: User?,
        articleId: Long
    ): ArticleReadResponseDto {
        TODO("Not yet implemented")
    }

    override fun findAllByKeywords(
        user: User?,
        keywords: List<String>
    ): List<ArticleReadResponseDto> {
        TODO("Not yet implemented")
    }
}