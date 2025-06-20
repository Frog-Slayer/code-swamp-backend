package dev.codeswamp.article.domain.exception.domain

enum class DomainNotFoundErrorCode (
    override  val code: String,
) : DomainErrorCode {
    DOMAIN_NOT_FOUND_ERROR("DOMAIN_NOT_FOUND_ERROR"),
    ARTICLE_NOT_FOUND("ARTICLE_NOT_FOUND_ERROR"),
    FOLDER_NOT_FOUND("FOLDER_NOT_FOUND_ERROR"),
}

abstract class DomainNotFoundException(
    errorCode: DomainNotFoundErrorCode,
    message: String
): DomainException( errorCode, message)