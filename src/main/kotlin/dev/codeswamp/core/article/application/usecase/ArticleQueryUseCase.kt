package dev.codeswamp.core.article.application.usecase

import dev.codeswamp.core.article.application.dto.query.GetArticleByIdQuery
import dev.codeswamp.core.article.application.dto.query.GetArticleByPathQuery
import dev.codeswamp.core.article.application.dto.query.GetVersionedArticleQuery
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.presentation.dto.response.ArticleReadResponseDto

interface ArticleQueryUseCase {
    fun findByArticleId(getArticleByIdQuery: GetArticleByIdQuery): VersionedArticle

    fun findAllByKeywords(userId: Long?, keywords: List<String>): List<ArticleReadResponseDto>//TODO

    fun getVersionedArticle(getVersionedArticleQuery: GetVersionedArticleQuery): VersionedArticle

    fun getArticleByUsernameAndPath(getArticleByPathQuery: GetArticleByPathQuery): VersionedArticle
}