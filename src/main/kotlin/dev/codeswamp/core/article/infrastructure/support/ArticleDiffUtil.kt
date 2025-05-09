package dev.codeswamp.core.article.infrastructure.support

import com.github.difflib.DiffUtils
import com.github.difflib.UnifiedDiffUtils
import dev.codeswamp.core.article.domain.support.ArticleDiffCalculator
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class ArticleDiffCalculatorImpl : ArticleDiffCalculator {
    override fun calculateDiff(old: String, new: String): String? {
        val oldLines = old?.lines()
        val newLines = new.lines()

        val patch = DiffUtils.diff(oldLines, newLines)

        if (patch.deltas.isEmpty()) {
            return null
        }

        return UnifiedDiffUtils.generateUnifiedDiff(
            "",
            "",
            oldLines,
            patch,
            3
        ).joinToString("\n")
    }
}