package dev.codeswamp.domain.article.application.service

import dev.codeswamp.domain.article.domain.model.Article
import dev.codeswamp.domain.article.presentation.dto.request.ArticleMetadataDto
import dev.codeswamp.domain.article.presentation.dto.response.ArticleReadResponseDto
import dev.codeswamp.domain.user.entity.User

interface ArticleApplicationService {

    fun create(user: User, metadata: ArticleMetadataDto, content: String)

    fun updateArticleMetadata(user: User, articleId: Long, metadata: ArticleMetadataDto)

    fun updateArticleContent(user: User, articleId: Long, content: String)

    fun delete(user: User, articleId: Long)

    fun findByArticleId(user: User?, articleId: Long): ArticleReadResponseDto

    fun findAllByKeywords(user: User?, keywords: List<String>): List<ArticleReadResponseDto>

}