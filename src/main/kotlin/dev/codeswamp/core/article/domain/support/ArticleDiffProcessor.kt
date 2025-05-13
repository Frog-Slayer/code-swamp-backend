package dev.codeswamp.core.article.domain.support

interface ArticleDiffProcessor {
    fun calculateDiff(old: String?, new: String) : String?
    fun buildFullContentFromHistory(snapshot: String, history: List<String>) : String
}