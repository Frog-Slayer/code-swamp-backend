package dev.codeswamp.articlequery.application.usecase.folder.byids

import dev.codeswamp.articlequery.application.readmodel.model.Folder

interface  GetAllFoldersByIdsUseCase {
    suspend fun handle(query: GetAllFoldersByIdsQuery): List<Folder>
}