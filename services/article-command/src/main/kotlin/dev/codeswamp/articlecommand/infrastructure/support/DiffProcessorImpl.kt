package dev.codeswamp.articlecommand.infrastructure.support

import com.github.difflib.DiffUtils
import com.github.difflib.UnifiedDiffUtils
import com.github.difflib.patch.Patch
import dev.codeswamp.articlecommand.domain.support.DiffProcessor
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

    override fun applyDiff(content: String, diff: String): String {
        val patch = UnifiedDiffUtils.parseUnifiedDiff(diff.lines())

        if (!patch.hasValidDelta()) return content

        return DiffUtils.patch(content.lines(), patch).joinToString("\n")
    }

    override fun hasValidDelta(diff: String) : Boolean{
        val patch = UnifiedDiffUtils.parseUnifiedDiff(diff.lines())
        return patch.hasValidDelta()
    }

    private fun Patch<String>.hasValidDelta(): Boolean {
        return this.deltas.any { delta ->
            delta.source.position >= 0 && delta.target.position >= 0
        }
    }
}