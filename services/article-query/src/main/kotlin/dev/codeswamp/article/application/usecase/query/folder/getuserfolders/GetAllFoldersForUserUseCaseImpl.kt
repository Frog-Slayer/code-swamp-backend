package dev.codeswamp.article.application.usecase.query.folder.getuserfolders

import dev.codeswamp.article.domain.folder.repository.FolderRepository
import org.springframework.stereotype.Service

@Service
class GetAllFoldersForUserUseCaseImpl(
    private val folderRepository: FolderRepository
) : GetAllFoldersForUserUseCase {
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