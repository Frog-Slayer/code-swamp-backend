package dev.codeswamp.articlecommand.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlecommand.domain.article.exception.InvalidArticleStateException
import dev.codeswamp.articlecommand.domain.article.model.Version
import dev.codeswamp.articlecommand.domain.article.repository.VersionRepository
import dev.codeswamp.articlecommand.infrastructure.persistence.graph.repository.VersionNodeRepository
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.VersionEntity
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.VersionStateJpa
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository.BaseVersionR2dbcRepository
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository.VersionR2dbcRepository
import org.springframework.stereotype.Repository

@Repository
class VersionRepositoryImpl(
    private val versionR2dbcRepository: VersionR2dbcRepository,
    private val versionNodeRepository: VersionNodeRepository,
    private val baseVersionR2dbcRepository: BaseVersionR2dbcRepository,
) : VersionRepository {
    override suspend fun save(version: Version): Version {
        return if (version.isNew) create(version)
        else update(version)
    }
    private suspend fun create(version: Version): Version {
        val entity = VersionEntity.Companion.from(version)

        val saved = versionR2dbcRepository.insert(
            id = entity.id,
            articleId = entity.articleId,
            prevVersionId = entity.previousVersionId,
            title = entity.title,
            diff = entity.diff,
            createdAt = entity.createdAt,
            state = entity.state.toString(),
            isBaseVersion = entity.isBaseVersion,
        )

        val fullContent = if (saved.isBaseVersion) {
            version.fullContent?.also {
                baseVersionR2dbcRepository.insert(saved.id, it)
            } ?: throw InvalidArticleStateException(
                    "Cannot create version",
                    "It is base version, but has no full content"
                )
        } else null

        return saved.toDomain(fullContent)
    }

    private suspend fun update(version: Version): Version {
        val entity = VersionEntity.Companion.from(version)
        val saved = versionR2dbcRepository.save(entity)

        return saved.restoreDomain()
    }


    override suspend fun findByIdOrNull(id: Long): Version? {
        val saved = versionR2dbcRepository.findById(id) ?: return null
        return saved.restoreDomain()
    }

    override suspend fun deleteAllByArticleIdIn(articleIds: List<Long>) {
        versionR2dbcRepository.deleteAllByArticleIdIn(articleIds)
        versionNodeRepository.deleteAllByArticleIdIn(articleIds) //TODO => 별도 이벤트로 분리
    }

    override suspend fun deleteByArticleId(articleId: Long) {
        versionR2dbcRepository.deleteAllByArticleId(articleId)
        versionNodeRepository.deleteAllByArticleId(articleId) // TODO => 별도 이벤트 분리
    }

    override suspend fun findPreviousPublishedVersion(articleId: Long, versionId: Long): Version? {
        return versionR2dbcRepository.findTopByArticleIdAndIdLessThanAndStateOrderByIdDesc(articleId, versionId, VersionStateJpa.PUBLISHED)
            ?.restoreDomain()
    }

    override suspend fun findNearestBaseTo(versionId: Long): Version? {
        return versionNodeRepository.findBaseNodeNearestTo(versionId)
            ?.let { versionR2dbcRepository.findById(it.versionId) }
            ?.restoreDomain()
    }

    override suspend fun findDiffChainBetween(baseId: Long, targetId: Long): List<String> {
        return versionNodeRepository.findShortestPathBetween(baseId, targetId)
            .map { it.versionId }
            .let { versionR2dbcRepository.findAllByIdIsIn(it) }
            .sortedBy { it.createdAt }
            .map { it.diff }
    }

    private suspend fun VersionEntity.restoreDomain(): Version {
        val fullContent = if (this.isBaseVersion) {
            baseVersionR2dbcRepository.findById(this.id)?.content
                ?: throw InvalidArticleStateException(
                    "Cannot restore version",
                    "It is base version, but has no full content"
                )
        } else null

        return this.toDomain(fullContent)
    }
}