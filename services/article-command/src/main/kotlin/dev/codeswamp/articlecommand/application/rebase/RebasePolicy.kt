package dev.codeswamp.articlecommand.application.rebase

import dev.codeswamp.articlecommand.domain.article.model.Version

interface RebasePolicy {
    suspend fun shouldStoreAsBase(version: Version): Boolean
}