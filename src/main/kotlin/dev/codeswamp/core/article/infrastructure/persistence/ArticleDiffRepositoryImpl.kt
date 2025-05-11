package dev.codeswamp.core.article.infrastructure.persistence

import dev.codeswamp.core.article.domain.model.ArticleDiff
import dev.codeswamp.core.article.domain.repository.ArticleDiffRepository
import dev.codeswamp.core.article.infrastructure.graph.repository.HistoryNodeRepository
import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleDiffEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.ArticleDiffJpaRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.ArticleJpaRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class ArticleDiffRepositoryImpl(
    private val diffJpaRepository: ArticleDiffJpaRepository,
    private val articleJpaRepository: ArticleJpaRepository,
    private val historyNodeRepository: HistoryNodeRepository,
) : ArticleDiffRepository {

    @Transactional
    override fun save(articleDiff: ArticleDiff): ArticleDiff {
        return diffJpaRepository.save(
            ArticleDiffEntity(
                article = articleJpaRepository.findById(articleDiff.articleId).orElse(null),
                previousVersion = articleDiff.previousVersionId?.let {
                    diffJpaRepository.findById(it).orElse(null)
                },
                diffData = articleDiff.diffData,
                createdAt = articleDiff.createdAt,
            )
        ).toDomain()
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

    override fun deleteByArticleId(articleId: Long) {
        diffJpaRepository.deleteAllByArticleId(articleId)
    }

    override fun findLCA(version1Id: Long, version2Id: Long): ArticleDiff? {
        val node1 = historyNodeRepository.findByDiffId(version1Id) ?: throw Exception("No node found")
        val node2 = historyNodeRepository.findByDiffId(version2Id) ?: throw Exception("No node found")

        val lca = historyNodeRepository.findLCA(node1.id ?: throw IllegalArgumentException("node1 ID is null"),
            node2.id ?: throw IllegalArgumentException("node2 ID is null"))
            ?: throw IllegalArgumentException("No LCA found between nodes ${node1.id} ${node2.id}")

        val lcaId = lca.id
            ?: throw Exception("cannot find common ancestor between two nodes")

        return findById(lcaId)
    }

    override fun findNearestSnapshotBefore(versionId: Long): ArticleDiff {
        val node = historyNodeRepository.findByDiffId(versionId) ?: throw Exception("No node found")

        val nearestSnapshotId =  historyNodeRepository.findNearestSnapshotBefore(node.id
            ?: throw Exception("no such node found")
        ).diffId

        return findById(nearestSnapshotId) ?: throw Exception("cannot find nearest snapshot")
    }

    override fun findDiffPathBetween(
        version1Id: Long,
        version2Id: Long
    ): List<ArticleDiff> {
        val node1 = historyNodeRepository.findByDiffId(version1Id) ?: throw Exception("No node found")
        val node2 = historyNodeRepository.findByDiffId(version2Id) ?: throw Exception("No node found")

        val path = historyNodeRepository.findPathBetween(node1.id ?: throw IllegalArgumentException("node1 ID is null"),
            node2.id ?: throw IllegalArgumentException("node2 ID is null")).map{ it.diffId }

        return findAllByIdsIn(path)
    }
}