package dev.codeswamp.core.article.application.service

import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto

interface ArticleQueryService {
    fun findByArticleId(userId: Long?, articleId: Long): ArticleReadResponseDto
    fun findAllByKeywords(userId: Long?, keywords: List<String>): List<ArticleReadResponseDto>//TODO

}