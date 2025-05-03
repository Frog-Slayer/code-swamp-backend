package dev.codeswamp.domain.article.application.service

import dev.codeswamp.domain.article.domain.service.ArticleService
import dev.codeswamp.domain.article.infrastructure.search.ArticleSearchRepository
import dev.codeswamp.domain.article.presentation.dto.request.ArticleMetadataDto
import dev.codeswamp.domain.article.presentation.dto.response.ArticleReadResponseDto
import dev.codeswamp.domain.user.entity.User
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