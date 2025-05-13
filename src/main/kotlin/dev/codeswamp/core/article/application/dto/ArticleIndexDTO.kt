package dev.codeswamp.core.article.application.dto

import dev.codeswamp.core.article.domain.model.Article

data class ArticleIndexDTO(
    val articleId: Long,
    val authorId: Long,
    val title: String,
    val preprocessedText: String,
    val isPublic: Boolean,
) {
    companion object {
        fun from(article: Article, preprocessedText: String) : ArticleIndexDTO {
            return ArticleIndexDTO(
                articleId = article.id!!,
                authorId = article.authorId,
                title = article.title,
                preprocessedText = preprocessedText,
                isPublic = article.isPublic,
            )
        }
    }
}