package dev.codeswamp.articlecommand.domain.exception.validation

import dev.codeswamp.articlecommand.domain.exception.domain.DomainBadRequestErrorCode
import dev.codeswamp.articlecommand.domain.exception.domain.DomainBadRequestException

//TODO 분리
class InvalidFormatException(
    message: String
) : DomainBadRequestException(
    DomainBadRequestErrorCode.INVALID_FORMAT,
    "Invalid format: $message"
)