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
        val originalContent = original?.content ?: ""

        val diff = diffProcessor.calculateDiff(originalContent, updated.content)

        return diff?.let {
            ArticleDiff(
                diffData = it,
                article = updated,
                createdAt = updated.updatedAt,
            )
        }
    }

    fun save(articleDiff: ArticleDiff): ArticleDiff? {
        return articleDiffRepository.save(articleDiff)
    }

    fun getHistory(articleId: Long) : List<ArticleDiff> {
        return articleDiffRepository.findByArticleId(articleId)
    }

    fun buildFullContentFromHistory(history: List<ArticleDiff>) : String {
        val sortedHistoryDiffList = history.sortedBy {  it.createdAt }.map{ it.diffData }
        return diffProcessor.buildFullContentFromHistory( sortedHistoryDiffList)
    }

    fun rollbackTo(article: Article, rollbackVersion: Long): Article {
        val currentVersion = article.currentVersion
        val lca = diffProcessor.findLCA(currentVersion,  rollbackVersion)
        val nearestSnapshotVersion = diffProcessor.findNearestSnapShotBefore(lca)

        val diffPathIdList = diffProcessor.findDiffPathBetween(nearestSnapshotVersion, rollbackVersion)
        val diffList = articleDiffRepository.findAllByIdsIn(diffPathIdList)

        val fullContent = buildFullContentFromHistory(diffList)

        return article.copy(
            content = fullContent,
            currentVersion = rollbackVersion,
            updatedAt = Instant.now()
        )
    }
}