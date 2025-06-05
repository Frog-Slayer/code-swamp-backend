package dev.codeswamp.core.article.infrastructure.support

import com.github.difflib.DiffUtils
import com.github.difflib.UnifiedDiffUtils
import dev.codeswamp.core.article.domain.support.DiffProcessor
import org.springframework.stereotype.Component

@Component
class DiffProcessorImpl : DiffProcessor {
    override fun calculateDiff(old: String?, new: String): String? {
        val oldLines = old?.lines() ?: emptyList()
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

    override fun buildFullContent(diffChain: List<String>): String {
        if (diffChain.isEmpty()) return ""

        val base = diffChain[0]
        var fullContent = base.lines()

        for (diff in  diffChain.drop(1)) {
            val patch = UnifiedDiffUtils.parseUnifiedDiff(diff.lines())
            fullContent = DiffUtils.patch(fullContent, patch)
        }
        return fullContent.joinToString("\n")
    }
}