package dev.codeswamp.core.article.infrastructure.support

import com.github.difflib.DiffUtils
import com.github.difflib.UnifiedDiffUtils
import dev.codeswamp.core.article.domain.support.ArticleDiffProcessor
import dev.codeswamp.core.article.infrastructure.graph.service.HistoryNodeService
import org.springframework.stereotype.Component

@Component
class ArticleDiffProcessorImpl(
    private val historyService: HistoryNodeService,
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

    override fun buildFullContentFromHistory(history: List<String>): String {
        if (history.isEmpty()) return ""

        var fullContent = "".lines()

        //var fullContent = history.first().lines()

        //for (diff in  history.drop(1)) {
        for (diff in  history) {
            val patch = UnifiedDiffUtils.parseUnifiedDiff(diff.lines())
            fullContent = DiffUtils.patch(fullContent, patch)
        }
        return fullContent.joinToString("\n")
    }

    override fun findLCA(versionAId: Long, versionBId: Long): Long {
        TODO("Not yet implemented")
        //return historyService.findLCA(versionAId, versionAId)
    }

    override fun findNearestSnapShotBefore(versionId: Long): Long {
        TODO("Not yet implemented")
    }

    override fun findDiffPathBetween(from: Long, to: Long): List<Long> {
        TODO("Not yet implemented")
        //return historyService.findPathBetweenNodes(from, to)
    }
}