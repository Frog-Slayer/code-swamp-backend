package dev.codeswamp.core.article.application.service

import dev.codeswamp.core.article.presentation.dto.request.ArticleMetadataDto
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto
import dev.codeswamp.core.user.entity.User

interface ArticleApplicationService {

    fun create(user: User, metadata: ArticleMetadataDto, content: String)

    fun updateArticleMetadata(user: User, articleId: Long, metadata: ArticleMetadataDto)

    fun updateArticleContent(user: User, articleId: Long, content: String)

    fun delete(user: User, articleId: Long)

    fun findByArticleId(user: User?, articleId: Long): ArticleReadResponseDto

    fun findAllByKeywords(user: User?, keywords: List<String>): List<ArticleReadResponseDto>

}