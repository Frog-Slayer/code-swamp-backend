package dev.codeswamp.articlecommand.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlecommand.domain.article.model.Version
import dev.codeswamp.articlecommand.domain.article.model.VersionState
import dev.codeswamp.articlecommand.domain.article.repository.VersionRepository
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.VersionEntity
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository.VersionR2dbcRepository
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

@Repository
class VersionRepositoryImpl(
    private val versionR2dbcRepository: VersionR2dbcRepository,
    private val databaseClient: DatabaseClient,
) : VersionRepository {

    override suspend fun insertVersions(versions: List<Version>) {
        if (versions.isEmpty()) return

        val entities = versions.map(VersionEntity::from)

        val ids = entities.map { it.id }.toTypedArray()
        val ownerIds = entities.map { it.ownerId }.toTypedArray()
        val articleIds = entities.map { it.articleId }.toTypedArray()
        val parentIds = entities.map { it.parentId }.toTypedArray()
        val titles = entities.map { it.title }.toTypedArray()
        val diffs = entities.map { it.diff }.toTypedArray()
        val createdAts = entities.map { it.createdAt }.toTypedArray()
        val states = entities.map { it.state }.toTypedArray()

        databaseClient.sql("""
            INSERT INTO version (id, owner_id, article_id, parent_id, title, diff, created_at, state)
            SELECT * FROM UNNEST( 
                :ids::bigint[],
                :ownerIds::bigint[],
                :articleIds::bigint[],
                :parentIds::bigint[],
                :titles::text[],
                :diffs::text[],
                :createdAts::timestamp[],
                :states::text[]
            )
        """)
            .bind("ids", ids)
            .bind("ownerIds", ownerIds)
            .bind("articleIds", articleIds)
            .bind("parentIds", parentIds)
            .bind("titles", titles)
            .bind("diffs",diffs)
            .bind("createdAts", createdAts)
            .bind("states", states)
            .fetch()
            .rowsUpdated()
            .awaitSingle()
    }

    override suspend fun updateVersions(versions: List<Version>) {
        if (versions.isEmpty()) return

        val entities = versions.map(VersionEntity::from)

        val ids = entities.map { it.id }.toTypedArray()
        val states = entities.map { it.state }.toTypedArray()

        databaseClient.sql("""
            UPDATE version v
            SET 
                state = u.state
            FROM ( 
                SELECT * FROM UNNEST( 
                    :ids::bigint[],
                    :states::text[]
                ) 
            ) AS u(id, state)
            WHERE v.id = u.id
        """)
            .bind("ids", ids)
            .bind("states", states)
            .fetch()
            .rowsUpdated()
            .awaitSingle()
    }

    override suspend fun findAllByArticleId(articleId: Long): List<Version> {
        return versionR2dbcRepository.findAllByArticleId(articleId).map {  it.toDomain() }
    }

    override suspend fun findByIdOrNull(id: Long): Version? {
        return versionR2dbcRepository.findById(id)?.toDomain()
    }

    override suspend fun deleteAllByArticleIdIn(articleIds: List<Long>) {
        versionR2dbcRepository.deleteAllByArticleIdIn(articleIds)
    }

    override suspend fun deleteByArticleId(articleId: Long) {
        versionR2dbcRepository.deleteAllByArticleId(articleId)
    }

    override suspend fun findByUserIdAndState (
        authorId: Long,
        state: VersionState
    ): List<Version> {
        return versionR2dbcRepository.findAllByOwnerIdAndState(authorId, state.name).map { it -> it.toDomain() }
    }
}