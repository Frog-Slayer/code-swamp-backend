package dev.codeswamp.projection.application.exception.article

import dev.codeswamp.projection.application.exception.application.AppForbiddenErrorCode
import dev.codeswamp.projection.application.exception.application.AppForbiddenException

class PrivateArticleException(
    override val message: String,
) : AppForbiddenException(AppForbiddenErrorCode.PRIVATE_ARTICLE, message)