package dev.codeswamp.core.article.domain.article.exception

import dev.codeswamp.core.article.domain.exception.domain.DomainForbiddenErrorCode
import dev.codeswamp.core.article.domain.exception.domain.DomainForbiddenException

class PrivateArticleForbiddenException(
    articleId: Long
) : DomainForbiddenException(
    DomainForbiddenErrorCode.FORBIDDEN_ARTICLE_ACCESS,
    "Article with ID $articleId is private"
)