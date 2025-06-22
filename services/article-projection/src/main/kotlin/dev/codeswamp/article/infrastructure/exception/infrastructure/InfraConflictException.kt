package dev.codeswamp.article.infrastructure.exception.infrastructure

enum class InfraConflictErrorCode(
    override val code: String,
) : InfraErrorCode {
    INFRA_CONFLICT("INFRA_CONFLICT"),
    ARTICLE_VERSION_MISMATCH("ARTICLE_VERSION_MISMATCH"),
}

abstract class InfraConflictException(
    errorCode: InfraConflictErrorCode,
    message: String
) : InfraException(errorCode, message)