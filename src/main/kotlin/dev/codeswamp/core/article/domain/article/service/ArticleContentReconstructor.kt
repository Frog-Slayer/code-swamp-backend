package dev.codeswamp.core.article.domain.article.service

import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.repository.VersionRepository
import dev.codeswamp.core.article.domain.support.DiffProcessor
import org.springframework.stereotype.Service

@Service
class ArticleContentReconstructor(
    private val versionRepository: VersionRepository,
    private val diffProcessor: DiffProcessor
){
    fun reconstructFullContent(article: VersionedArticle) : String {
        val base = versionRepository.findNearestBaseTo(article.currentVersion.id)
                    ?: throw IllegalStateException("Base Versiond을 찾지 못했습니다")

        val baseContent = requireNotNull(base.fullContent)

        val diffChain = versionRepository.findDiffChainBetween(base.id, article.currentVersion.id)

        return diffProcessor.buildFullContent(baseContent, diffChain)
    }

    fun applyDiff(content: String, diff: String) : String {
        return diffProcessor.applyDiff(content, diff)
    }
}