package dev.codeswamp.articlecommand.application.exception.article

import dev.codeswamp.articlecommand.application.exception.application.AppNotFoundErrorCode
import dev.codeswamp.articlecommand.application.exception.application.AppNotFoundException

class ArticleNotFoundException(
    message: String,
) : AppNotFoundException(
    AppNotFoundErrorCode.ARTICLE_NOT_FOUND,
    message
) {
    companion object {
        fun byId(id: Long) = ArticleNotFoundException("Article with ID $id not found")
        fun bySlug(slug: String) = ArticleNotFoundException("Article with slug $slug not found in the folder")
    }
}