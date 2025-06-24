package dev.codeswamp.articlecommand.application.rebase

import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle

interface SnapshotPolicy {
    suspend fun shouldSaveAsSnapshot( article: VersionedArticle ): Boolean
}