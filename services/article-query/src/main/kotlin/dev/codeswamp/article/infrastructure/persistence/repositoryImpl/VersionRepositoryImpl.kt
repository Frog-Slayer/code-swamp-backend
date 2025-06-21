package dev.codeswamp.article.infrastructure.persistence.repositoryImpl

import dev.codeswamp.article.domain.article.model.Version
import dev.codeswamp.article.domain.article.repository.VersionRepository
import dev.codeswamp.article.infrastructure.event.event.VersionNodeSaveEvent
import dev.codeswamp.article.infrastructure.persistence.graph.repository.VersionNodeRepository
import dev.codeswamp.article.infrastructure.persistence.jpa.entity.BaseVersionEntity
import dev.codeswamp.article.infrastructure.persistence.jpa.entity.VersionEntity
import dev.codeswamp.article.infrastructure.persistence.jpa.entity.VersionStateJpa
import dev.codeswamp.article.infrastructure.persistence.jpa.repository.BaseVersionJpaRepository
import dev.codeswamp.article.infrastructure.persistence.jpa.repository.VersionJpaRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class VersionRepositoryImpl(
    private val versionJpaRepository: VersionJpaRepository,
    private val versionNodeRepository: VersionNodeRepository,
    private val baseVersionJpaRepository: BaseVersionJpaRepository,
    private val eventPublisher: ApplicationEventPublisher
) : VersionRepository {
    override suspend fun save(version: Version): Version {
        val entity = VersionEntity.Companion.from(version)
        val existingEntity = versionJpaRepository.findByIdOrNull(entity.id)

        if (version.isBaseVersion && version.fullContent != null) {
            baseVersionJpaRepository.save(BaseVersionEntity(version.id, version.fullContent))
        }

        val ret = if (existingEntity != null) {
            existingEntity.updateTo(entity)
            existingEntity.toDomain()
        }
        else versionJpaRepository.save(entity).toDomain()

        eventPublisher.publishEvent(
            VersionNodeSaveEvent(
                versionId = version.id,
                articleId = version.articleId,
                isBase = version.isBaseVersion,
                previousNodeId = version.previousVersionId
            )
        )

        return ret;
    }

    override suspend fun findByIdOrNull(id: Long): Version? {
        return versionJpaRepository.findByIdOrNull(id)?.toDomain()
    }

    override suspend fun deleteAllByArticleIdIn(articleIds: List<Long>) {
        versionJpaRepository.deleteAllByArticleIdIn(articleIds)
        versionNodeRepository.deleteAllByArticleIdIn(articleIds) //TODO => 별도 이벤트로 분리
    }

    override suspend fun deleteByArticleId(articleId: Long) {
        versionJpaRepository.deleteAllByArticleId(articleId)
        versionNodeRepository.deleteAllByArticleId(articleId) // TODO => 별도 이벤트 분리
    }

    override suspend fun findPreviousPublishedVersion(articleId: Long, versionId: Long): Version? {
        return versionJpaRepository.findTopByArticleIdAndIdLessThanAndStateOrderByIdDesc(articleId, versionId, VersionStateJpa.PUBLISHED)?.toDomain()
    }

    override suspend fun findNearestBaseTo(versionId: Long): Version? {
       return versionNodeRepository.findBaseNodeNearestTo(versionId)
                        ?.let { versionJpaRepository.findByIdOrNull(it.versionId) }
                        ?.toDomain()
    }

    override suspend fun findDiffChainBetween(baseId: Long, targetId: Long): List<String> {
        return versionNodeRepository.findShortestPathBetween(baseId, targetId)
                .map { it.versionId }
                .let { versionJpaRepository.findAllByIdIsIn(it)}
                .sortedBy { it.createdAt }
                .map { it.diff }
    }

    fun VersionEntity.toDomain() : Version {
        val fullContent = if (this.isBaseVersion) baseVersionJpaRepository.findByIdOrNull(id)?.content else null
        return this.toDomain(fullContent)
    }
}