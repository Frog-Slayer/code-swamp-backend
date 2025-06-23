package dev.codeswamp.articlecommand.domain.exception.domain

import dev.codeswamp.core.common.exception.ForbiddenErrorCode
import dev.codeswamp.core.common.exception.ForbiddenException

enum class DomainForbiddenErrorCode(
    override val code: String,
) : ForbiddenErrorCode {
    DOMAIN_FORBIDDEN_ERROR("DOMAIN_FORBIDDEN_ERROR"),
    FORBIDDEN_FOLDER_ACCESS("FORBIDDEN_FOLDER_ACCESS"),
    FORBIDDEN_ARTICLE_ACCESS("FORBIDDEN_ARTICLE_ACCESS"),
}

abstract class DomainForbiddenException(
    errorCode: DomainForbiddenErrorCode,
    message: String
) : ForbiddenException(errorCode, message)