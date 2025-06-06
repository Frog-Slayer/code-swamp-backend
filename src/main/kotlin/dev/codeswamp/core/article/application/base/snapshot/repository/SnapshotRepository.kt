package dev.codeswamp.core.article.application.base.snapshot.repository

import dev.codeswamp.core.article.application.base.snapshot.model.Snapshot

interface SnapshotRepository {
    fun countVersionsOfArticle(articleId: Long) : Long
}