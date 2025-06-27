package dev.codeswamp.articleprojection.application.exception.article

import dev.codeswamp.articleprojection.application.exception.application.AppForbiddenErrorCode
import dev.codeswamp.articleprojection.application.exception.application.AppForbiddenException
import dev.codeswamp.core.common.exception.ForbiddenErrorCode

class PrivateArticleException(
    override val message: String,
) : AppForbiddenException(AppForbiddenErrorCode.PRIVATE_ARTICLE, message)