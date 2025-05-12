package dev.codeswamp.core.article.infrastructure

import dev.codeswamp.core.article.domain.model.ArticleDiff
import dev.codeswamp.core.article.domain.repository.ArticleDiffRepository
import dev.codeswamp.core.article.infrastructure.graph.node.HistoryNode
import dev.codeswamp.core.article.infrastructure.graph.repository.HistoryNodeRepository
import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleDiffEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.ArticleDiffJpaRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.ArticleJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ArticleDiffRepositoryImpl(
    private val diffJpaRepository: ArticleDiffJpaRepository,
    private val articleJpaRepository: ArticleJpaRepository,
    private val historyNodeRepository: HistoryNodeRepository,
) : ArticleDiffRepository {

    //TODO 원자성 보장 필요
    override fun save(articleDiff: ArticleDiff): ArticleDiff {
        val article = articleJpaRepository.findById(articleDiff.articleId).orElse(null)
        val prevVersion = articleDiff.previousVersionId?.let {
                    diffJpaRepository.findById(it).orElse(null)
                }

        val saved = diffJpaRepository.save(
            ArticleDiffEntity(
                article = article,
                isSnapshot = articleDiff.isSnapshot,
                snapshotContent = articleDiff.snapshotContent,
                previousVersion = prevVersion,
                diffData = articleDiff.diffData,
                createdAt = articleDiff.createdAt,
            )
        )

        historyNodeRepository.save(
            HistoryNode(
                diffId = saved.id!!,
                articleId = article.id!!,
                isSnapshot = saved.isSnapshot,
                previous = prevVersion?.id?.let { historyNodeRepository.findByDiffId(it) }
            )
        )

        return saved.toDomain()
    }

    override fun findById(id: Long): ArticleDiff? {
        return diffJpaRepository.findById(id).orElse(null).toDomain()
    }

    override fun findByArticleId(articleId: Long): List<ArticleDiff> {
        return diffJpaRepository.findAllByArticleId(articleId).map { it.toDomain() }
    }

    override fun countByArticleId(articleId: Long): Long {
        return diffJpaRepository.countByArticleId(articleId)
    }

    override fun findAllByIdsIn(diffs: List<Long>): List<ArticleDiff> {
        return diffJpaRepository.findAllByIdIsIn(diffs).map { it.toDomain() }
    }

    //TODO 원자성 보장 필요
    override fun deleteByArticleId(articleId: Long) {
        diffJpaRepository.deleteAllByArticleId(articleId)
        historyNodeRepository.deleteAllByArticleId(articleId)
    }

    override fun findLCA(version1Id: Long, version2Id: Long): ArticleDiff? {
        val lca = historyNodeRepository.findLCA(version1Id, version2Id)
            ?: throw IllegalArgumentException("No LCA found between nodes $version1Id $version2Id")

        val lcaDiffId= lca.diffId

        return findById(lcaDiffId)
    }

    override fun findNearestSnapshotBefore(versionId: Long): ArticleDiff {
        val nearestSnapshotId =  historyNodeRepository.findNearestSnapshotBefore(versionId).diffId

        return findById(nearestSnapshotId) ?: throw Exception("cannot find nearest snapshot")
    }

    override fun findDiffPathBetween(
        version1Id: Long,
        version2Id: Long
    ): List<ArticleDiff> {
        val path = historyNodeRepository.findPathBetween(version1Id, version2Id).map{ it.diffId }

        return findAllByIdsIn(path)
    }
}