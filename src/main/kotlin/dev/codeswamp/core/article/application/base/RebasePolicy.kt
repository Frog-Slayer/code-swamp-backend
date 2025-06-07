package dev.codeswamp.core.article.application.base

import dev.codeswamp.core.article.domain.article.model.Version

interface RebasePolicy {
    fun shouldStoreAsBase(version: Version): Boolean
}