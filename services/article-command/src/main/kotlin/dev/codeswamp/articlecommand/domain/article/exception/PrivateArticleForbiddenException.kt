package dev.codeswamp.articlecommand.domain.article.exception

import dev.codeswamp.articlecommand.domain.exception.domain.DomainForbiddenErrorCode
import dev.codeswamp.articlecommand.domain.exception.domain.DomainForbiddenException

class PrivateArticleForbiddenException(
    articleId: Long
) : DomainForbiddenException(
    DomainForbiddenErrorCode.FORBIDDEN_ARTICLE_ACCESS,
    "Article with ID $articleId is private"
)