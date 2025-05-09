package dev.codeswamp.core.article.domain.support

interface ArticleDiffProcessor {
    fun calculateDiff(old: String, new: String) : String?
    fun buildFullContentFromHistory(history: List<String>) : String
    fun findLCA(versionAId: Long, versionBId: Long): Long
    fun findNearestSnapShotBefore(versionId: Long): Long
}