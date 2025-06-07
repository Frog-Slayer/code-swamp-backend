package dev.codeswamp.core.article.domain.article.exception

class ArticleNotFoundException(
    articleId: Long,
) : DomainNotFoundException("Cannot find article for ID $articleId")