package dev.codeswamp.articlequery.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlequery.application.readmodel.model.Folder
import dev.codeswamp.articlequery.application.readmodel.repository.FolderRepository
import dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.entity.FolderEntity
import dev.codeswamp.databasequery.QuerySpecBuilder
import dev.codeswamp.databasequery.executeQueryList
import dev.codeswamp.databasequery.executeQuerySingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

@Repository
class FolderRepositoryImpl(
    private val databaseClient: DatabaseClient
): FolderRepository {
    val tableName = "folder"

    override suspend fun findById(
        folderId: Long,
        fields: Set<String>
    ): Folder? {
        val querySpec = QuerySpecBuilder(tableName)
            .select(*fields.toTypedArray())
            .where("id = :folderId")
            .bind("folderId", folderId)
            .build()

        return databaseClient.executeQuerySingle<FolderEntity, Folder>(querySpec) {
            it.toDomain()
        }
    }

    override suspend fun findAllByIds(
        folderIds: List<Long>,
        fields: Set<String>
    ): List<Folder> {
        if ( folderIds.isEmpty() ) return emptyList()

        val querySpec = QuerySpecBuilder(tableName)
           .select(*fields.toTypedArray())
           .where("id IN (:folderId)")
           .bind("folderId", folderIds)
           .build()

        return databaseClient.executeQueryList<FolderEntity, Folder>(querySpec) {
            it.toDomain()
        }
    }

    override suspend fun findFolderByFolderPath(
        folderPath: String,
        fields: Set<String>
    ): Folder? {
        val querySpec = QuerySpecBuilder(tableName)
           .select(*fields.toTypedArray())
           .where("full_path = :folderPath")
           .bind("folderPath", folderPath)
           .build()

        return databaseClient.executeQuerySingle<FolderEntity, Folder>(querySpec) {
            it.toDomain()
        }
    }

    override suspend fun findAllByOwnerId(
        userId: Long,
        fields: Set<String>
    ): List<Folder> {
        val querySpec = QuerySpecBuilder(tableName)
           .select(*fields.toTypedArray())
           .where("owner_id = :userId")
           .bind("userId", userId)
           .build()

        return databaseClient.executeQueryList<FolderEntity, Folder>(querySpec) {
            it.toDomain()
        }
    }
}