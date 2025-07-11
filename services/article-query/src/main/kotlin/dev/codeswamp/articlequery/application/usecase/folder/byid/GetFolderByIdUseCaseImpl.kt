package dev.codeswamp.articlequery.application.usecase.folder.byid

import dev.codeswamp.articlequery.application.exception.folder.FolderNotFoundException
import dev.codeswamp.articlequery.application.readmodel.model.Folder
import dev.codeswamp.articlequery.application.readmodel.repository.FolderRepository
import org.springframework.stereotype.Service

@Service
class GetFolderByIdUseCaseImpl (
    private val folderRepository: FolderRepository,
) : GetFolderByIdUseCase{
    override suspend fun handle(query: GetFolderByIdQuery): Folder {
        return folderRepository.findById(query.folderId, query.fields)
            ?: throw FolderNotFoundException.byId(query.folderId)
    }
}