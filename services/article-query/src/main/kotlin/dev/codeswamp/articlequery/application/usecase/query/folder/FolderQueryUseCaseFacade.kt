package dev.codeswamp.articlequery.application.usecase.query.folder

import dev.codeswamp.articlequery.application.usecase.query.folder.getuserfolders.GetAllFoldersForUserQuery
import dev.codeswamp.articlequery.application.usecase.query.folder.getuserfolders.GetAllFoldersForUserResult
import dev.codeswamp.articlequery.application.usecase.query.folder.getuserfolders.GetAllFoldersForUserUseCase
import org.springframework.stereotype.Service

@Service
class FolderQueryUseCaseFacade(
    private val getAllFoldersForUserUseCase: GetAllFoldersForUserUseCase,
) {

    suspend fun getUserFolders(query: GetAllFoldersForUserQuery): GetAllFoldersForUserResult {
        return getAllFoldersForUserUseCase.handle(query)
    }
}