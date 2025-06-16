package dev.codeswamp.core.article.application.usecase.query.folder.getuserfolders

interface GetAllFoldersForUserUseCase {
    fun handle (query: GetAllFoldersForUserQuery) : GetAllFoldersForUserResult
}