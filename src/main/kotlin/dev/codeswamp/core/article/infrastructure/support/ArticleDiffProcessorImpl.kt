package dev.codeswamp.core.article.infrastructure.support

import com.github.difflib.DiffUtils
import com.github.difflib.UnifiedDiffUtils
import dev.codeswamp.core.article.domain.support.ArticleDiffProcessor
import org.springframework.stereotype.Component

@Component
class ArticleDiffProcessorImpl : ArticleDiffProcessor {
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
        TODO("Not yet implemented")
    }

    override fun findLCA(versionAId: Long, versionBId: Long): Long {
        TODO("Not yet implemented")
    }

    override fun findNearestSnapShotBefore(versionId: Long): Long {
        TODO("Not yet implemented")
    }

    override fun findDiffPathBetween(from: Long, to: Long): List<Long> {
        TODO("Not yet implemented")
    }
}