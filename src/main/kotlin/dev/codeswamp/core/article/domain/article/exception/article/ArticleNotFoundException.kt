package dev.codeswamp.core.article.domain.article.exception.article

import dev.codeswamp.core.article.domain.article.exception.domain.DomainNotFoundException

class ArticleNotFoundException(
    articleId: Long,
) : DomainNotFoundException("Cannot find article for ID $articleId")