package dev.codeswamp.core.article.application.usecase.query.folder

import dev.codeswamp.core.article.application.usecase.query.folder.getuserfolders.GetAllFoldersForUserQuery
import dev.codeswamp.core.article.application.usecase.query.folder.getuserfolders.GetAllFoldersForUserResult
import dev.codeswamp.core.article.application.usecase.query.folder.getuserfolders.GetAllFoldersForUserUseCase
import org.springframework.stereotype.Service

@Service
class FolderQueryUseCaseFacade(
    private val getAllFoldersForUserUseCase: GetAllFoldersForUserUseCase,
){

    fun getUserFolders(query: GetAllFoldersForUserQuery) : GetAllFoldersForUserResult {
        return getAllFoldersForUserUseCase.handle(query)
    }
}