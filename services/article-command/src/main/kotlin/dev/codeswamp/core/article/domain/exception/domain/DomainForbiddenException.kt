package dev.codeswamp.core.article.domain.exception.domain

enum class DomainForbiddenErrorCode(
    override val code: String,
): DomainErrorCode {
    DOMAIN_FORBIDDEN_ERROR("DOMAIN_FORBIDDEN_ERROR"),
    FORBIDDEN_FOLDER_ACCESS("FORBIDDEN_FOLDER_ACCESS"),
    FORBIDDEN_ARTICLE_ACCESS("FORBIDDEN_ARTICLE_ACCESS"),
}

abstract class DomainForbiddenException (
    errorCode: DomainForbiddenErrorCode,
    message: String
): DomainException( errorCode, message )