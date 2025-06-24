package dev.codeswamp.articlecommand.application.rebase

import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle

interface SnapshotPolicy {
    suspend fun shouldSaveSnapshot(article: VersionedArticle): Boolean
}