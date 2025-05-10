package dev.codeswamp.core.article.infrastructure.support

import com.github.difflib.DiffUtils
import com.github.difflib.UnifiedDiffUtils
import dev.codeswamp.core.article.domain.support.ArticleDiffProcessor
import dev.codeswamp.core.article.infrastructure.graph.service.HistoryNodeService
import org.springframework.stereotype.Component

@Component
class ArticleDiffProcessorImpl(
    private val historyNodeService: HistoryNodeService,
) : ArticleDiffProcessor {
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

    override fun buildFullContentFromHistory(snapshot: String, history: List<String>): String {
        if (history.isEmpty()) return ""

        var fullContent = snapshot.lines()

        for (diff in  history.drop(1)) {
            val patch = UnifiedDiffUtils.parseUnifiedDiff(diff.lines())
            fullContent = DiffUtils.patch(fullContent, patch)
        }
        return fullContent.joinToString("\n")
    }

    override fun findLCA(versionAId: Long, versionBId: Long): Long {
        return historyNodeService.findLCA(versionAId, versionAId)
    }

    override fun findNearestSnapShotBefore(versionId: Long): Long {
        return historyNodeService.findNearestSnapShotBefore(versionId)
    }

    override fun findDiffPathBetween(from: Long, to: Long): List<Long> {
        return historyNodeService.findPathBetweenNodes(from, to)
    }
}