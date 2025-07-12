package dev.codeswamp.articlequery.application.usecase.folder.byowner

import dev.codeswamp.articlequery.application.readmodel.model.Folder

interface GetAllFoldersOfUserUseCase {
    suspend fun handle(query: GetAllFoldersOfUserQuery) : List<Folder>
}