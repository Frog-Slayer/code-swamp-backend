package dev.codeswamp.core.article.infrastructure.persistence.repositoryImpl

import dev.codeswamp.core.article.domain.article.model.Version
import dev.codeswamp.core.article.domain.article.repository.VersionRepository
import dev.codeswamp.core.article.infrastructure.events.VersionNodeSaveEvent
import dev.codeswamp.core.article.infrastructure.persistence.graph.repository.VersionNodeRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.BaseVersionEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.VersionEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.VersionStateJpa
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.BaseVersionJpaRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.VersionJpaRepository
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
    override fun save(version: Version): Version {
        val entity = VersionEntity.from(version)
        val existingEntity = versionJpaRepository.findByIdOrNull(entity.id)

        if (version.isBaseVersion && version.fullContent != null) {
            baseVersionJpaRepository.save(BaseVersionEntity(version.id, version.fullContent))
        }

        val ret = if (existingEntity != null) {
            existingEntity.updateTo(entity)
            existingEntity.toDomain()
        }
        else versionJpaRepository.save(entity).toDomain()

        eventPublisher.publishEvent(VersionNodeSaveEvent(
            versionId = version.id,
            articleId = version.articleId,
            isBase = version.isBaseVersion,
            previousNodeId = version.previousVersionId
        ))

        return ret;
    }

    override fun findByIdOrNull(id: Long): Version? {
        return versionJpaRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun deleteByArticleId(articleId: Long) {
        versionJpaRepository.deleteAllByArticleId(articleId)
        versionNodeRepository.deleteAllByArticleId(articleId)
    }

    override fun findPreviousPublishedVersion(articleId: Long, versionId: Long): Version? {
        return versionJpaRepository.findTopByArticleIdAndIdLessThanAndStateOrderByIdDesc(articleId, versionId,VersionStateJpa.PUBLISHED)?.toDomain()
    }

    override fun findNearestBaseTo(versionId: Long): Version? {
       return versionNodeRepository.findBaseNodeNearestTo(versionId)
                        ?.let { versionJpaRepository.findByIdOrNull(it.versionId) }
                        ?.toDomain()
    }

    override fun findDiffChainBetween(baseId: Long, targetId: Long): List<String> {
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