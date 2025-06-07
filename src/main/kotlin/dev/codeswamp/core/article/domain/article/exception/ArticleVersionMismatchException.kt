package dev.codeswamp.core.article.domain.article.exception

class ArticleVersionMismatchException(
    articleId: Long,
    versionId: Long
) : DomainConflictException("Article($articleId) is not related to Version($versionId)")