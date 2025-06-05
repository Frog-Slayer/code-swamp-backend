package dev.codeswamp.core.article.infrastructure.persistence.repositoryImpl

import dev.codeswamp.core.article.domain.article.model.Version
import dev.codeswamp.core.article.domain.article.repository.VersionRepository
import dev.codeswamp.core.article.infrastructure.persistence.graph.repository.VersionNodeRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.ArticleStatusJpa
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.VersionJpaRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.ArticleMetadataJpaRepository
import org.springframework.stereotype.Repository

@Repository
class VersionRepositoryImpl(
    private val versionJpaRepository: VersionJpaRepository,
    private val versionNodeRepository: VersionNodeRepository,
) : VersionRepository {
    override fun findByIdOrNull(id: Long): Version? {
        return versionJpaRepository.findById(id).orElse(null).toDomain()
    }

    override fun countByArticleId(articleId: Long): Long {
        return versionJpaRepository.countByArticleId(articleId)
    }

    override fun deleteByArticleId(articleId: Long) {
        versionJpaRepository.deleteAllByArticleId(articleId)
        versionNodeRepository.deleteAllByArticleId(articleId)
    }

    override fun findPublishedVersionByArticleId(articleId: Long): Version? {
        return versionJpaRepository.findByArticleIdAndState(articleId, ArticleStatusJpa.PUBLISHED)?.toDomain()
    }

    override fun findDiffChainFromNearestSnapshot(versionId: Long): List<String> {
        return versionNodeRepository.findDiffChainFromNearestSnapshot(versionId)
            .map { it.versionId }
            .let { versionJpaRepository.findAllByIdIsIn(it)}
            .map { it.diff }
    }

}