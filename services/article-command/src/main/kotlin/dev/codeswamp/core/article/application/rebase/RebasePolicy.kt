package dev.codeswamp.core.article.application.rebase

import dev.codeswamp.core.article.domain.article.model.Version

interface RebasePolicy {
    fun shouldStoreAsBase(version: Version): Boolean
}