package dev.codeswamp.articlecommand.application.rebase

import dev.codeswamp.articlecommand.domain.article.model.Article

interface SnapshotPolicy {
    suspend fun shouldSaveAsSnapshot(article: Article ): Boolean
}