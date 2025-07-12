package dev.codeswamp.articlequery.application.usecase.folder.bypath

import dev.codeswamp.articlequery.application.readmodel.model.Folder

interface  GetFolderByPathUseCase {
    suspend fun handle(query: GetFolderByPathQuery): Folder
}