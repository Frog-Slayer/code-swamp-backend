package dev.codeswamp.articlecommand.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlecommand.domain.article.model.Version
import dev.codeswamp.articlecommand.domain.article.repository.VersionRepository
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.VersionEntity
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository.VersionR2dbcRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class VersionRepositoryImpl(
    private val versionR2dbcRepository: VersionR2dbcRepository,
    private val databaseClient: DatabaseClient,
) : VersionRepository {

    override suspend fun insertVersions(versions: List<Version>) {
        databaseClient.inConnectionMany { connection ->
            val statement = connection.createStatement("""
                    INSERT INTO version (id, article_id, parent_id, title, diff, created_at, state)
                    VALUES ($1, $2, $3, $4, $5, $6, $7)
            """)

            versions.forEach {  version ->
                val entity = VersionEntity.from(version)
                statement
                    .bind(0, entity.id)
                    .bind(1, entity.articleId)
                    .bind(4, entity.diff)
                    .bind(5, entity.createdAt)
                    .bind(6, entity.state)
                    .apply{
                        if ( entity.parentId != null) bind(2, entity.parentId)
                        else bindNull(2, Long::class.java)

                        if ( entity.title != null) bind(3, entity.title)
                        else bindNull(3,String::class.java)
                    }
                    .add()
            }

            Flux.from(statement.execute())
        }.awaitFirstOrNull()
    }

    override suspend fun updateVersions(versions: List<Version>) {
        databaseClient.inConnectionMany { connection ->
            val statement = connection.createStatement("""
                    UPDATE version
                    SET 
                        article_id = $2,
                        parent_id = $3,
                        title = $4,
                        diff = $5,
                        created_at = $6,
                        state = $7,
                    WHERE id = $1
            """)

            versions.forEach {  version ->
                val entity = VersionEntity.from(version)
                statement
                    .bind(0, entity.id)
                    .bind(1, entity.articleId)
                    .bind(4, entity.diff)
                    .bind(5, entity.createdAt)
                    .bind(6, entity.state)
                    .apply{
                        if ( entity.parentId != null) bind(2, entity.parentId)
                        else bindNull(2, Long::class.java)

                        if ( entity.title != null) bind(3, entity.title)
                        else bindNull(3,String::class.java)
                    }
                    .add()
            }

            Flux.from(statement.execute())
        }.awaitFirstOrNull()
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
}