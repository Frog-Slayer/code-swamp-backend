package dev.codeswamp.article.application.rebase

import dev.codeswamp.article.domain.article.model.Version

interface RebasePolicy {
    fun shouldStoreAsBase(version: Version): Boolean
}