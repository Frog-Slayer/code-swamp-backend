package dev.codeswamp.articlecommand.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlecommand.domain.article.model.Version
import dev.codeswamp.articlecommand.domain.article.repository.VersionRepository
import dev.codeswamp.articlecommand.infrastructure.event.event.VersionNodeSaveEvent
import dev.codeswamp.articlecommand.infrastructure.persistence.graph.repository.VersionNodeRepository
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.VersionEntity
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.VersionStateJpa
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository.BaseVersionR2dbcRepository
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository.VersionR2dbcRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Repository

@Repository
class VersionRepositoryImpl(
    private val versionR2dbcRepository: VersionR2dbcRepository,
    private val versionNodeRepository: VersionNodeRepository,
    private val baseVersionR2dbcRepository: BaseVersionR2dbcRepository,
    private val eventPublisher: ApplicationEventPublisher//TODO: InfraEvent
) : VersionRepository {
    override suspend fun create(version: Version): Version {
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

        eventPublisher.publishEvent(
            VersionNodeSaveEvent(
                versionId = version.id,
                articleId = version.articleId,
                isBase = version.isBaseVersion,
                previousNodeId = version.previousVersionId
            )
        )

        return saved.toDomain()
    }

    override suspend fun save(version: Version): Version {
        val entity = VersionEntity.Companion.from(version)

        val saved = versionR2dbcRepository.save(entity)

        eventPublisher.publishEvent(
            VersionNodeSaveEvent(
                versionId = version.id,
                articleId = version.articleId,
                isBase = version.isBaseVersion,
                previousNodeId = version.previousVersionId
            )
        )

        return saved.toDomain()
    }

    override suspend fun findByIdOrNull(id: Long): Version? {
        return versionR2dbcRepository.findById(id)?.toDomain()
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
            ?.toDomain()
    }

    override suspend fun findNearestBaseTo(versionId: Long): Version? {
        return versionNodeRepository.findBaseNodeNearestTo(versionId)
            ?.let { versionR2dbcRepository.findById(it.versionId) }
            ?.toDomain()
    }

    override suspend fun findDiffChainBetween(baseId: Long, targetId: Long): List<String> {
        return versionNodeRepository.findShortestPathBetween(baseId, targetId)
            .map { it.versionId }
            .let { versionR2dbcRepository.findAllByIdIsIn(it) }
            .sortedBy { it.createdAt }
            .map { it.diff }
    }

    suspend fun VersionEntity.toDomain(): Version {
        val fullContent = if (this.isBaseVersion) baseVersionR2dbcRepository.findById(id)?.content else null
        return this.toDomain(fullContent)
    }
}