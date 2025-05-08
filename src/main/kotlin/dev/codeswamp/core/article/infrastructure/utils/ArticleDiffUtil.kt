package dev.codeswamp.core.article.infrastructure.utils

import com.github.difflib.DiffUtils
import com.github.difflib.UnifiedDiffUtils

object ArticleDiffUtil {
    fun generateDiff(oldText: String, newText: String) : String {
        val oldLines = oldText.lines()
        val newLines = newText.lines()

        val patch = DiffUtils.diff(oldLines, newLines)

        return UnifiedDiffUtils.generateUnifiedDiff(
            "",
            "",
            oldLines,
            patch,
            3
        ).joinToString("\n")
    }
}