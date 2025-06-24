package dev.codeswamp.articlecommand.domain.article.service

import dev.codeswamp.articlecommand.domain.article.exception.ContentReconstructionException
import dev.codeswamp.articlecommand.domain.article.model.Version
import dev.codeswamp.articlecommand.domain.article.repository.VersionRepository
import dev.codeswamp.articlecommand.domain.support.DiffProcessor
import org.springframework.stereotype.Service

@Service
class ArticleContentReconstructor(
    private val versionRepository: VersionRepository,
    private val diffProcessor: DiffProcessor
) {
    //TODO diffChain & applyDiff 실패를 대비 try-catch로 예외 처리 필요
    suspend fun reconstructFullContent(version: Version): String {
        return when {
            version.snapshot != null -> contentFromSnapshot(version)
            version.previousVersionId == null -> contentFromInitialDiff(version.diff)
            else -> contentFromDiffChain(version)
        }
    }

    fun contentFromInitialDiff(diff: String): String {
        return applyDiff("", diff)
    }

    private fun contentFromSnapshot(version: Version): String {
        return version.snapshot!!.fullContent
    }

    private suspend fun contentFromDiffChain(version: Version): String {
        val previousVersionId = version.previousVersionId
            ?: throw ContentReconstructionException("version ${version.id} has no previous version")

        val base = versionRepository.findNearestBaseTo(previousVersionId)
            ?: throw ContentReconstructionException("version ${version.id} has no base version")

        val diffChain = versionRepository.findDiffChainBetween(base.id, version.previousVersionId)

        val previousContent = diffProcessor.buildFullContent(
            base.snapshot?.fullContent
                ?: throw ContentReconstructionException("base version ${base.id} has no content"),
            diffChain
        )

        return applyDiff(previousContent, version.diff)
    }


    fun applyDiff(content: String, diff: String): String {
        return diffProcessor.applyDiff(content, diff)
    }

}