package dev.codeswamp.core.article.domain.exception.domain

enum class DomainBadRequestErrorCode(
    override val code: String,
) : DomainErrorCode {
    DOMAIN_BAD_REQUEST_ERROR("DOMAIN_BAD_REQUEST_ERROR"),
    DUPLICATED_FOLDER_NAME("DUPLICATED_FOLDER_NAME"),
    DUPLICATED_SLUG("DUPLICATED_SLUG"),
    INVALID_ARTICLE_STATE("INVALID_ARTICLE_STATE"),
    REQUEST_TO_RENAME_ROOT("REQUEST_TO_RENAME_ROOT"),
    REQUEST_TO_DELETE_ROOT("REQUEST_TO_DELETE_ROOT"),
    INVALID_FOLDER_MOVE("INVALID_FOLDER_MOVE"),
    INVALID_FORMAT("INVALID_FORMAT"),
}

abstract class DomainBadRequestException(
    errorCode: DomainBadRequestErrorCode,
    message: String
): DomainException(errorCode, message)