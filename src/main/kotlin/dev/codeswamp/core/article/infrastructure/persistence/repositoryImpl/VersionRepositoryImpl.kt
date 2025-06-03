package dev.codeswamp.core.article.infrastructure.persistence.repositoryImpl

import dev.codeswamp.core.article.domain.article.model.Version
import dev.codeswamp.core.article.domain.article.repository.VersionRepository
import dev.codeswamp.core.article.infrastructure.persistence.graph.node.VersionNode
import dev.codeswamp.core.article.infrastructure.persistence.graph.repository.VersionNodeRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.VersionEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.VersionJpaRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.ArticleJpaRepository
import org.springframework.stereotype.Repository

@Repository
class VersionRepositoryImpl(
    private val diffJpaRepository: VersionJpaRepository,
    private val articleJpaRepository: ArticleJpaRepository,
    private val versionNodeRepository: VersionNodeRepository,
) : VersionRepository {

    //TODO 원자성 보장 필요
    override fun save(version: Version): Version {
        val article = articleJpaRepository.findById(version.articleId).orElse(null)
        val prevVersion = version.previousVersionId?.let {
                    diffJpaRepository.findById(it).orElse(null)
                }

        val saved = diffJpaRepository.save(
            VersionEntity(
                article = article,
                isSnapshot = version.isSnapshot,
                snapshotContent = version.snapshotContent,
                previousVersion = prevVersion,
                diffData = version.diff,
                createdAt = version.createdAt,
            )
        )

        versionNodeRepository.save(
            VersionNode(
                diffId = saved.id!!,
                articleId = article.id!!,
                isSnapshot = saved.isSnapshot,
                previous = prevVersion?.id?.let { versionNodeRepository.findByDiffId(it) }
            )
        )

        return saved.toDomain()
    }

    override fun findById(id: Long): Version? {
        return diffJpaRepository.findById(id).orElse(null).toDomain()
    }

    override fun findByArticleId(articleId: Long): List<Version> {
        return diffJpaRepository.findAllByArticleId(articleId).map { it.toDomain() }
    }

    override fun countByArticleId(articleId: Long): Long {
        return diffJpaRepository.countByArticleId(articleId)
    }

    override fun findAllByIdsIn(diffs: List<Long>): List<Version> {
        return diffJpaRepository.findAllByIdIsIn(diffs).map { it.toDomain() }
    }

    //TODO 원자성 보장 필요
    override fun deleteByArticleId(articleId: Long) {
        diffJpaRepository.deleteAllByArticleId(articleId)
        versionNodeRepository.deleteAllByArticleId(articleId)
    }

    override fun findLCA(version1Id: Long, version2Id: Long): Version? {
        val lca = versionNodeRepository.findLCA(version1Id, version2Id)
            ?: throw IllegalArgumentException("No LCA found between nodes $version1Id $version2Id")

        val lcaDiffId= lca.diffId

        return findById(lcaDiffId)
    }

    override fun findNearestSnapshotBefore(versionId: Long): Version {
        val nearestSnapshotId =  versionNodeRepository.findNearestSnapshotBefore(versionId).diffId

        return findById(nearestSnapshotId) ?: throw Exception("cannot find nearest snapshot")
    }

    override fun findDiffPathBetween(
        version1Id: Long,
        version2Id: Long
    ): List<Version> {
        val path = versionNodeRepository.findPathBetween(version1Id, version2Id).map{ it.diffId }

        return findAllByIdsIn(path)
    }
}