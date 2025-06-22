package dev.codeswamp.articlecommand.domain.article.exception

import dev.codeswamp.articlecommand.domain.exception.domain.DomainBadRequestErrorCode
import dev.codeswamp.articlecommand.domain.exception.domain.DomainBadRequestException

class InvalidArticleStateException(
    message: String,
    reason: String
) : DomainBadRequestException(
    DomainBadRequestErrorCode.INVALID_ARTICLE_STATE,
    "$message: $reason"
)