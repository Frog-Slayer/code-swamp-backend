package dev.codeswamp.core.article.domain.article.service

import dev.codeswamp.core.article.domain.article.model.Article
import dev.codeswamp.core.article.domain.article.model.ArticleDiff
import dev.codeswamp.core.article.domain.article.repository.ArticleDiffRepository
import dev.codeswamp.core.article.domain.support.ArticleDiffProcessor
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ArticleHistoryService(
    private val articleDiffRepository: ArticleDiffRepository,
    private val diffProcessor: ArticleDiffProcessor
) {
    fun save(articleDiff: ArticleDiff): ArticleDiff {
        return articleDiffRepository.save(articleDiff)
    }

    fun findById(id: Long): ArticleDiff? {
        return articleDiffRepository.findById(id)
    }

    fun getHistory(articleId: Long) : List<ArticleDiff> {
        return articleDiffRepository.findByArticleId(articleId)
    }

    fun deleteArticle(articleId: Long) {
        articleDiffRepository.deleteByArticleId(articleId)
    }

    fun getRollbackedArticle(article: Article, rollbackVersionId: Long): Article {
        val rollbackVersion = findById(rollbackVersionId) ?: throw IllegalArgumentException("Rollback not found")
        if (rollbackVersion.articleId != article.id) throw IllegalArgumentException("version does not match")

        val currentVersion = article.currentVersion ?: throw Exception("currentVersion is not set")

        val lca = articleDiffRepository.findLCA(currentVersion, rollbackVersionId)?.id ?: throw Exception("The two versions do not belong to the same article")

        val nearestSnapshotVersion = articleDiffRepository.findNearestSnapshotBefore(lca).id ?: throw Exception("id not set")

        val diffList = articleDiffRepository.findDiffPathBetween(nearestSnapshotVersion,rollbackVersionId)

        val fullContent = buildFullContentFromHistory(diffList[0].snapshotContent
            ?: throw Exception("Failed to load snapshot content")
            , diffList)

        return article.copy(
            content = fullContent,
            currentVersion = rollbackVersionId,
            updatedAt = Instant.now()
        )
    }

    fun calculateDiff(original: Article?, updated: Article) : ArticleDiff? {
        val originalContent = original?.content
        val versionCount = original?.id?.let{ articleDiffRepository.countByArticleId(it)} ?: 0

        val diff = diffProcessor.calculateDiff(originalContent, updated.content)
        val prevVersion = original?.currentVersion
        val isSnapshot = (versionCount % 10 == 0L)

        return diff?.let {
            ArticleDiff(
                diffData = it,
                isSnapshot = isSnapshot,
                snapshotContent = if (isSnapshot) updated.content else null,
                previousVersionId = prevVersion,
                articleId = updated.id!!,
                createdAt = updated.updatedAt,
            )
        }
    }


    fun buildFullContentFromHistory(snapshot: String, history: List<ArticleDiff>) : String {
        val sortedHistoryDiffList = history.sortedBy {  it.createdAt }.map{ it.diffData }
        return diffProcessor.buildFullContentFromHistory(snapshot, sortedHistoryDiffList)
    }
}