package dev.codeswamp.core.article.domain.article.exception.article

import dev.codeswamp.core.article.domain.article.exception.domain.DomainForbiddenException

class PrivateArticleForbiddenException(
    articleId: String
) : DomainForbiddenException("Article with ID $articleId is private")