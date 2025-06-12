package dev.codeswamp.core.article.domain.article.exception

import dev.codeswamp.core.article.domain.exception.domain.DomainBadRequestErrorCode
import dev.codeswamp.core.article.domain.exception.domain.DomainBadRequestException

class InvalidArticleStateException(
    message: String,
    reason: String
) : DomainBadRequestException(
    DomainBadRequestErrorCode.INVALID_ARTICLE_STATE,
    "$message: $reason"
)