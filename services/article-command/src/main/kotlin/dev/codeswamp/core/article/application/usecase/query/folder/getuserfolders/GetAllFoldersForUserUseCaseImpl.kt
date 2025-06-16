package dev.codeswamp.core.article.application.usecase.query.folder.getuserfolders

import dev.codeswamp.core.article.domain.folder.repository.FolderRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.FolderEntity
import org.springframework.stereotype.Service

@Service
class GetAllFoldersForUserUseCaseImpl(
    private val folderRepository: FolderRepository
): GetAllFoldersForUserUseCase {
    override fun handle(query: GetAllFoldersForUserQuery): GetAllFoldersForUserResult {
        val folders = folderRepository.findAllByOwnerId(query.userId)

        return GetAllFoldersForUserResult(
            folders.map { folder ->
                FolderInfo(
                    id = folder.id,
                    name = folder.name.value,
                    parentId = folder.parentId
                )
            }
        )
    }
}