package dev.codeswamp.core.article.domain.article.service

import dev.codeswamp.core.article.domain.article.exception.article.ContentReconstructionException
import dev.codeswamp.core.article.domain.article.model.Version
import dev.codeswamp.core.article.domain.article.repository.VersionRepository
import dev.codeswamp.core.article.domain.support.DiffProcessor
import org.springframework.stereotype.Service

@Service
class ArticleContentReconstructor(
    private val versionRepository: VersionRepository,
    private val diffProcessor: DiffProcessor
){
    //TODO
    fun reconstructFullContent(version: Version) : String {
        return when {
            version.isBaseVersion -> contentFromBaseVersion(version)
            version.previousVersionId == null -> contentFromInitialDiff(version.diff)
            else -> contentFromDiffChain(version)
        }
    }

    fun contentFromInitialDiff(diff: String) : String {
        return applyDiff("", diff)
    }

    private fun contentFromBaseVersion(version: Version) : String {
        return version.fullContent
            ?: throw ContentReconstructionException("base version ${version.id} has no content")
    }

    private fun contentFromDiffChain(version: Version): String {
        val previousVersionId = version.previousVersionId
            ?: throw ContentReconstructionException("version ${version.id} has no previous version")

        val base = versionRepository.findNearestBaseTo(previousVersionId)
            ?: throw ContentReconstructionException("version ${version.id} has no base version")

        val diffChain = versionRepository.findDiffChainBetween(base.id, version.previousVersionId)

        val previousContent = diffProcessor.buildFullContent(
            base.fullContent
                ?: throw ContentReconstructionException("base version ${base.id} has no content"),
            diffChain
        )

        return applyDiff(previousContent, version.diff)
    }


    private fun applyDiff(content: String, diff: String) : String {
        return diffProcessor.applyDiff(content, diff)
    }

}