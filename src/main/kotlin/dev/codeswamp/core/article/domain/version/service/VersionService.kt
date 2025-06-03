package dev.codeswamp.core.article.domain.version.service

import dev.codeswamp.core.article.domain.article.model.Article
import dev.codeswamp.core.article.domain.support.ArticleDiffProcessor
import dev.codeswamp.core.article.domain.version.model.Version
import dev.codeswamp.core.article.domain.version.repository.VersionRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class VersionService(
    private val versionRepository: VersionRepository,
    private val diffProcessor: ArticleDiffProcessor
) {
    fun save(version: Version): Version {
        return versionRepository.save(version)
    }

    fun findById(id: Long): Version? {
        return versionRepository.findById(id)
    }

    fun getHistory(articleId: Long) : List<Version> {
        return versionRepository.findByArticleId(articleId)
    }

    fun deleteArticle(articleId: Long) {
        versionRepository.deleteByArticleId(articleId)
    }

    fun getRollbackedArticle(article: Article, rollbackVersionId: Long): Article {
        val rollbackVersion = findById(rollbackVersionId) ?: throw IllegalArgumentException("Rollback not found")
        if (rollbackVersion.articleId != article.id) throw IllegalArgumentException("version does not match")

        val currentVersion = article.currentVersion ?: throw Exception("currentVersion is not set")

        val lca = versionRepository.findLCA(currentVersion, rollbackVersionId)?.id ?: throw Exception("The two versions do not belong to the same article")

        val nearestSnapshotVersion = versionRepository.findNearestSnapshotBefore(lca).id ?: throw Exception("id not set")

        val diffList = versionRepository.findDiffPathBetween(nearestSnapshotVersion,rollbackVersionId)

        val fullContent = buildFullContentFromHistory(diffList[0].snapshotContent
            ?: throw Exception("Failed to load snapshot content")
            , diffList)

        return article.copy(
            content = fullContent,
            currentVersion = rollbackVersionId,
            updatedAt = Instant.now()
        )
    }

    fun calculateDiff(original: Article?, updated: Article) : Version? {
        val originalContent = original?.content
        val versionCount = original?.id?.let{ versionRepository.countByArticleId(it)} ?: 0

        val diff = diffProcessor.calculateDiff(originalContent, updated.content)
        val prevVersion = original?.currentVersion
        val isSnapshot = (versionCount % 10 == 0L)

        return diff?.let {
            Version(
                diff = it,
                isSnapshot = isSnapshot,
                snapshotContent = if (isSnapshot) updated.content else null,
                previousVersionId = prevVersion,
                articleId = updated.id!!,
                createdAt = updated.updatedAt,
            )
        }
    }


    fun buildFullContentFromHistory(snapshot: String, history: List<Version>) : String {
        val sortedHistoryDiffList = history.sortedBy {  it.createdAt }.map{ it.diff }
        return diffProcessor.buildFullContentFromHistory(snapshot, sortedHistoryDiffList)
    }
}