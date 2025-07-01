package dev.codeswamp.projection.application.dto.command

import dev.codeswamp.projection.application.readmodel.model.PublishedArticle

data class IndexArticleCommand(
    val articleId: Long,
    val authorId: Long,
    val title: String,
    val preprocessedText: String,
    val isPublic: Boolean,
) {
    companion object {
        fun from(article : PublishedArticle, preprocessedText: String): IndexArticleCommand {
            return IndexArticleCommand(
                articleId = article.id,
                authorId = article.authorId,
                title = article.title,
                preprocessedText = preprocessedText,
                isPublic = article.isPublic,
            )
        }
    }
}