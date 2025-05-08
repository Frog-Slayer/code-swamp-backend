package dev.codeswamp.core.article.application.service

import dev.codeswamp.core.article.presentation.dto.request.ArticleMetadataDto
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto
import dev.codeswamp.core.user.infrastructure.persistence.entity.UserEntity

interface ArticleApplicationService {

    fun create(userId: Long, metadata: ArticleMetadataDto, content: String)

    fun updateArticleMetadata(userId: Long, articleId: Long, metadata: ArticleMetadataDto)

    fun updateArticleContent(userId: Long, articleId: Long, content: String)

    fun delete(userId: Long, articleId: Long)

    fun findByArticleId(userId: Long?, articleId: Long): ArticleReadResponseDto

    fun findAllByKeywords(userId: Long?, keywords: List<String>): List<ArticleReadResponseDto>

}