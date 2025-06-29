package dev.codeswamp.article.infrastructure.support

import com.github.difflib.DiffUtils
import com.github.difflib.UnifiedDiffUtils
import com.github.difflib.patch.Patch
import dev.codeswamp.article.domain.support.DiffProcessor
import org.springframework.stereotype.Component

@Component
class DiffProcessorImpl : DiffProcessor {
    override fun calculateDiff(old: String?, new: String): String {//FOR TEST ONLY
        val oldLines = old?.lines() ?: emptyList()
        val newLines = new.lines()

        val patch = DiffUtils.diff(oldLines, newLines)

        return UnifiedDiffUtils.generateUnifiedDiff(
            "",
            "",
            oldLines,
            patch,
            3
        ).joinToString("\n")
    }


    override fun buildFullContent(base: String, diffChain: List<String>): String {
        if (diffChain.isEmpty()) return ""

        var fullContent = base.lines()

        for (diff in diffChain.drop(1)) {
            val patch = UnifiedDiffUtils.parseUnifiedDiff(diff.lines())
            if (!patch.hasValidDelta()) continue;

            fullContent = DiffUtils.patch(fullContent, patch)
        }
        return fullContent.joinToString("\n")
    }

    override fun applyDiff(content: String, diff: String): String {
        val patch = UnifiedDiffUtils.parseUnifiedDiff(diff.lines())

        if (!patch.hasValidDelta()) return content;

        return DiffUtils.patch(content.lines(), patch).joinToString("\n")
    }

    private fun Patch<String>.hasValidDelta(): Boolean {
        return this.deltas.any { delta ->
            delta.source.position >= 0 && delta.target.position >= 0
        }
    }
}