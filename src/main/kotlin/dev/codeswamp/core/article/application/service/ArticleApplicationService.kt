package dev.codeswamp.core.article.application.service

import dev.codeswamp.core.article.presentation.dto.request.ArticleMetadataDto
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto
import dev.codeswamp.core.user.infrastructure.entity.UserEntity

interface ArticleApplicationService {

    fun create(user: UserEntity, metadata: ArticleMetadataDto, content: String)

    fun updateArticleMetadata(user: UserEntity, articleId: Long, metadata: ArticleMetadataDto)

    fun updateArticleContent(user: UserEntity, articleId: Long, content: String)

    fun delete(user: UserEntity, articleId: Long)

    fun findByArticleId(user: UserEntity?, articleId: Long): ArticleReadResponseDto

    fun findAllByKeywords(user: UserEntity?, keywords: List<String>): List<ArticleReadResponseDto>

}