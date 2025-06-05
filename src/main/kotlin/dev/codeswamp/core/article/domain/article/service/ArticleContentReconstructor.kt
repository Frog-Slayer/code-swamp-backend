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
        val diffChain = versionRepository.findDiffChainFromNearestSnapshot(article.currentVersion.id)
        return diffProcessor.buildFullContent(diffChain)
    }
}