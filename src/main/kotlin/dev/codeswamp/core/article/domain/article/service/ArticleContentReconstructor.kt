package dev.codeswamp.core.article.domain.article.service

import dev.codeswamp.core.article.domain.article.model.Version
import dev.codeswamp.core.article.domain.article.repository.VersionRepository
import dev.codeswamp.core.article.domain.support.DiffProcessor
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
class ArticleContentReconstructor(
    private val versionRepository: VersionRepository,
    private val diffProcessor: DiffProcessor
){
    fun reconstructFullContent(version: Version) : String {
        if (version.isBaseVersion) return requireNotNull(version.fullContent) { "this version should be a base version but has no content"}
        if (version.previousVersionId == null) return applyDiff("", version.diff)

        val base = versionRepository.findNearestBaseTo(version.previousVersionId) ?: throw IllegalStateException("no base version found")
        val diffChain = versionRepository.findDiffChainBetween(base.id, version.previousVersionId)

        val previousVersionFullContent = diffProcessor.buildFullContent(requireNotNull(base.fullContent), diffChain)

        return diffProcessor.applyDiff(previousVersionFullContent, version.diff)
    }

    fun applyDiff(content: String, diff: String) : String {
        return diffProcessor.applyDiff(content, diff)
    }
}