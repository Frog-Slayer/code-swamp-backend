package dev.codeswamp.core.article.infrastructure.exception.article

import dev.codeswamp.core.article.infrastructure.exception.infrastructure.InfraConflictErrorCode
import dev.codeswamp.core.article.infrastructure.exception.infrastructure.InfraConflictException

class ArticleVersionMismatchException(
    articleId: Long,
    versionId: Long
) : InfraConflictException(
    InfraConflictErrorCode.ARTICLE_VERSION_MISMATCH,
    "Version $versionId does not belong to Article $articleId"
)