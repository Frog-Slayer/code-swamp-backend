package dev.codeswamp.articlequery.application.exception.article

import dev.codeswamp.articlequery.application.exception.application.AppNotFoundErrorCode
import dev.codeswamp.articlequery.application.exception.application.AppNotFoundException

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