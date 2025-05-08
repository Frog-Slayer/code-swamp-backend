package dev.codeswamp.core.article.application.service

import dev.codeswamp.core.article.domain.service.ArticleService
import dev.codeswamp.core.article.infrastructure.search.ArticleSearchRepository
import dev.codeswamp.core.article.presentation.dto.request.ArticleMetadataDto
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto
import dev.codeswamp.core.user.infrastructure.persistence.entity.UserEntity
import org.springframework.stereotype.Service

@Service
class ArticleApplicationServiceImpl(
    private val articleService: ArticleService,
    private val articleSearchRepository: ArticleSearchRepository,
    ) : ArticleApplicationService {

    override fun create(
        userId: Long,
        metadata: ArticleMetadataDto,
        content: String
    ) {
        TODO("Not yet implemented")
    }

    override fun updateArticleMetadata(
        userId: Long,
        articleId: Long,
        metadata: ArticleMetadataDto
    ) {
        TODO("Not yet implemented")
    }

    override fun updateArticleContent(userId: Long, articleId: Long, content: String) {
        TODO("Not yet implemented")
    }

    override fun delete(userId: Long, articleId: Long) {
        TODO("Not yet implemented")
    }

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