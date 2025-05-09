package dev.codeswamp.core.article.domain.support

import dev.codeswamp.core.article.domain.model.Article

interface ArticleDiffCalculator {
    fun calculateDiff(old: String, new: String) : String?
}