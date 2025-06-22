package dev.codeswamp.article.domain.article.exception

import dev.codeswamp.article.domain.exception.domain.DomainBadRequestErrorCode
import dev.codeswamp.article.domain.exception.domain.DomainBadRequestException

class InvalidArticleStateException(
    message: String,
    reason: String
) : DomainBadRequestException(
    DomainBadRequestErrorCode.INVALID_ARTICLE_STATE,
    "$message: $reason"
)