package dev.codeswamp.core.article.application.service

import dev.codeswamp.core.article.application.dto.query.GetArticleByIdQuery
import dev.codeswamp.core.article.application.dto.query.GetArticleByPathQuery
import dev.codeswamp.core.article.application.dto.query.GetVersionedArticleQuery
import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto

interface ArticleQueryService {
    fun findByArticleId(getArticleByIdQuery: GetArticleByIdQuery): Article

    fun findAllByKeywords(userId: Long?, keywords: List<String>): List<ArticleReadResponseDto>//TODO

    fun getVersionedArticle(getVersionedArticleQuery: GetVersionedArticleQuery): Article

    fun getArticleByUsernameAndPath(getArticleByPathQuery: GetArticleByPathQuery): Article
}