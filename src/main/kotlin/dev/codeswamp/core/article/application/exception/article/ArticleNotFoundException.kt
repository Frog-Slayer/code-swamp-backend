package dev.codeswamp.core.article.application.exception.article

import dev.codeswamp.core.article.application.exception.application.AppNotFoundException

class ArticleNotFoundException(
    message: String,
) : AppNotFoundException(message) {

    companion object {
        fun byId(id: Long) = ArticleNotFoundException("Article with ID $id not found")
        fun bySlug(slug: String) = ArticleNotFoundException("Article with slug $slug not found in the folder")
    }
}