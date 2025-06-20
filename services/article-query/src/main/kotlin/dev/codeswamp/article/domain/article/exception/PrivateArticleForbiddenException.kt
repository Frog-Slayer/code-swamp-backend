package dev.codeswamp.article.domain.article.exception

import dev.codeswamp.article.domain.exception.domain.DomainForbiddenErrorCode
import dev.codeswamp.article.domain.exception.domain.DomainForbiddenException

class PrivateArticleForbiddenException(
    articleId: Long
) : DomainForbiddenException(
    DomainForbiddenErrorCode.FORBIDDEN_ARTICLE_ACCESS,
    "Article with ID $articleId is private"
)