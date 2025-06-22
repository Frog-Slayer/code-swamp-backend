package dev.codeswamp.articlecommand.domain.support

interface DiffProcessor {
    fun calculateDiff(old: String?, new: String): String?
    fun buildFullContent(base: String, diffChain: List<String>): String
    fun applyDiff(content: String, diff: String): String
    fun hasValidDelta(diff: String) : Boolean
}