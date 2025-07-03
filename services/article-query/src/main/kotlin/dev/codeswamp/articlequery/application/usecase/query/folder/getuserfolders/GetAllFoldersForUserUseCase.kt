package dev.codeswamp.articlequery.application.usecase.query.folder.getuserfolders

interface GetAllFoldersForUserUseCase {
    suspend fun handle(query: GetAllFoldersForUserQuery): GetAllFoldersForUserResult
}