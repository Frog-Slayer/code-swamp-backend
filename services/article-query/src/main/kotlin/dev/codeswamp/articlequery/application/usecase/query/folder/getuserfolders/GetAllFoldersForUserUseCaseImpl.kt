package dev.codeswamp.articlequery.application.usecase.query.folder.getuserfolders

import dev.codeswamp.articlequery.application.readmodel.repository.FolderRepository
import org.springframework.stereotype.Service

@Service
class GetAllFoldersForUserUseCaseImpl(
    private val folderRepository: FolderRepository
) : GetAllFoldersForUserUseCase {
    override suspend fun handle(query: GetAllFoldersForUserQuery): GetAllFoldersForUserResult {
        val folders = folderRepository.findAllByOwnerId(query.userId)

        return GetAllFoldersForUserResult(
            folders.map { folder ->
                FolderInfo(
                    id = folder.id,
                    name = folder.name,
                    parentId = folder.parentId
                )
            }
        )
    }
}