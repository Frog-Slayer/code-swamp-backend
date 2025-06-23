package dev.codeswamp.articlecommand.application.exception.article

import dev.codeswamp.articlecommand.application.exception.application.AppBadRequestErrorCode
import dev.codeswamp.articlecommand.application.exception.application.AppBadRequestException

class ArticleVersionMismatchException(
    articleId: Long,
    versionId: Long
) : AppBadRequestException (
    AppBadRequestErrorCode.APP_ARTICLE_VERSION_MISMATCH,
    "Version $versionId does not belong to Article $articleId"
)