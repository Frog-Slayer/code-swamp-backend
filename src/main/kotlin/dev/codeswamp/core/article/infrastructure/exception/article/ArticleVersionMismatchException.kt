package dev.codeswamp.core.article.infrastructure.exception.article

import dev.codeswamp.core.article.infrastructure.exception.infrastructure.InfraConflictException

class ArticleVersionMismatchException(
    articleId: Long,
    versionId: Long
) : InfraConflictException("Version $versionId does not belong to Article $articleId")