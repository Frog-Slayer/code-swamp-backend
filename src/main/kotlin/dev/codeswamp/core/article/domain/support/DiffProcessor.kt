package dev.codeswamp.core.article.domain.support

interface DiffProcessor {
    fun calculateDiff(old: String?, new: String) : String?
    fun buildFullContent (diffChain: List<String>) : String
}