package dev.codeswamp.core.article.domain.service

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.model.ArticleDiff
import dev.codeswamp.core.article.domain.repository.ArticleDiffRepository
import dev.codeswamp.core.article.domain.support.ArticleDiffProcessor
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ArticleHistoryService(
    private val articleDiffRepository: ArticleDiffRepository,
    private val diffProcessor: ArticleDiffProcessor
) {
    fun calculateDiff(original: Article?, updated: Article) : ArticleDiff? {
        val originalContent = original?.content
        val diff = diffProcessor.calculateDiff(originalContent, updated.content)
        val prevVersion = original?.currentVersion

        return diff?.let {
            ArticleDiff(
                diffData = it,
                previousVersionId = prevVersion,
                article = updated,
                createdAt = updated.updatedAt,
            )
        }
    }

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

    fun buildFullContentFromHistory(history: List<ArticleDiff>) : String {
        val sortedHistoryDiffList = history.sortedBy {  it.createdAt }.map{ it.diffData }
        return diffProcessor.buildFullContentFromHistory( sortedHistoryDiffList)
    }

    fun rollbackTo(article: Article, rollbackVersionId: Long): Article {
        val rollbackVersion = findById(rollbackVersionId) ?: throw IllegalArgumentException("Rollback not found")
        if (rollbackVersion.article.id != article.id) throw IllegalArgumentException("version does not match")

        val currentVersion = article.currentVersion
        val lca = diffProcessor.findLCA(currentVersion,  rollbackVersionId)
        val nearestSnapshotVersion = diffProcessor.findNearestSnapShotBefore(lca)

        val diffPathIdList = diffProcessor.findDiffPathBetween(nearestSnapshotVersion, rollbackVersionId)
        val diffList = articleDiffRepository.findAllByIdsIn(diffPathIdList)

        val fullContent = buildFullContentFromHistory(diffList)

        return article.copy(
            content = fullContent,
            currentVersion = rollbackVersionId,
            updatedAt = Instant.now()
        )
    }
}