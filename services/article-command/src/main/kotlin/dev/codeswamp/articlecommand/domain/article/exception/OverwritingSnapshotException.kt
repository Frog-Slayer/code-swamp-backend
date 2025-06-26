package dev.codeswamp.articlecommand.domain.article.exception

import dev.codeswamp.articlecommand.domain.exception.domain.DomainBadRequestErrorCode
import dev.codeswamp.articlecommand.domain.exception.domain.DomainBadRequestException

class OverwritingSnapshotException(
    message: String,
) : DomainBadRequestException(
    DomainBadRequestErrorCode.OVERWRITE_SNAPSHOT_ERROR,
    message
)