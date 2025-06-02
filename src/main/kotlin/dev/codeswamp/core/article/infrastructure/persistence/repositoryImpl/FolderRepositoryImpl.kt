package dev.codeswamp.core.article.infrastructure.persistence.repositoryImpl

import dev.codeswamp.core.article.domain.folder.model.Folder
import dev.codeswamp.core.article.domain.folder.repository.FolderRepository
import dev.codeswamp.core.article.infrastructure.persistence.graph.node.FolderNode
import dev.codeswamp.core.article.infrastructure.persistence.graph.repository.FolderNodeRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.FolderEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.FolderJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class FolderRepositoryImpl(
    private val folderJpaRepository: FolderJpaRepository,
    private val folderNodeRepository: FolderNodeRepository
) : FolderRepository {

    override fun save(folder: Folder): Folder {
        val parent = folder.parentId?.let{folderJpaRepository.findByIdOrNull(folder.parentId)}
        val saved = folderJpaRepository.save(FolderEntity.Companion.from(folder, parent)).toDomain()

        val parentNode = parent?.id?.let {folderNodeRepository.findByFolderId(it)}
        val node = FolderNode.Companion.from(saved, parentNode)

        folderNodeRepository.save(node)
        return saved
    }

    override fun delete(folder: Folder) {
        if (folder.id == null) throw IllegalArgumentException("folder id is required")
        val savedEntity = folderJpaRepository.findByIdOrNull(folder.id) ?: throw IllegalArgumentException("Folder with id ${folder.id} not found in RDB")
        val savedNode = folderNodeRepository.findByFolderId(folder.id) ?: throw IllegalArgumentException("Folder with id ${folder.id} not found in neo4j")

        folderJpaRepository.delete(savedEntity)
        folderNodeRepository.delete(savedNode)
    }

    override fun findById(folderId: Long): Folder? {
        return folderJpaRepository.findByIdOrNull(folderId)?.toDomain()
    }

    override fun findAllByIds(folderIds: List<Long>): List<Folder> {
        return folderJpaRepository.findAllById(folderIds).map { it.toDomain() }
    }

    override fun findFolderByFullPath(rootName: String, paths: List<String>): Folder? {
        val targetFolderId = folderNodeRepository.findFolderIdByFullPath(rootName, paths)
        return folderJpaRepository.findByIdOrNull(targetFolderId)?.toDomain()
    }

    override fun existsByParentIdAndName(parentId: Long, name: String): Boolean {
       return folderJpaRepository.existsByParentIdAndName(parentId, name)
    }

}