package dev.codeswamp.articlequery.application.usecase.folder.byid

import dev.codeswamp.articlequery.application.readmodel.model.Folder

interface  GetFolderByIdUseCase {
    suspend fun handle(query: GetFolderByIdQuery): Folder
}