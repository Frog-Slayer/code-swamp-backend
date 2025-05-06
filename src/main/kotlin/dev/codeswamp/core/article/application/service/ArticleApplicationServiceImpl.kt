package dev.codeswamp.core.article.application.service

import dev.codeswamp.core.article.domain.service.ArticleService
import dev.codeswamp.core.article.infrastructure.search.ArticleSearchRepository
import dev.codeswamp.core.article.presentation.dto.request.ArticleMetadataDto
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto
import dev.codeswamp.core.user.infrastructure.entity.UserEntity
import org.springframework.stereotype.Service

@Service
class ArticleApplicationServiceImpl(
    private val articleService: ArticleService,
    private val articleSearchRepository: ArticleSearchRepository,
    ) : ArticleApplicationService {

    override fun create(
        user: UserEntity,
        metadata: ArticleMetadataDto,
        content: String
    ) {
        TODO("Not yet implemented")
    }

    override fun updateArticleMetadata(
        user: UserEntity,
        articleId: Long,
        metadata: ArticleMetadataDto
    ) {
        TODO("Not yet implemented")
    }

    override fun updateArticleContent(user: UserEntity, articleId: Long, content: String) {
        TODO("Not yet implemented")
    }

    override fun delete(user: UserEntity, articleId: Long) {
        TODO("Not yet implemented")
    }

    override fun findByArticleId(
        user: UserEntity?,
        articleId: Long
    ): ArticleReadResponseDto {
        TODO("Not yet implemented")
    }

    override fun findAllByKeywords(
        user: UserEntity?,
        keywords: List<String>
    ): List<ArticleReadResponseDto> {
        TODO("Not yet implemented")
    }
}