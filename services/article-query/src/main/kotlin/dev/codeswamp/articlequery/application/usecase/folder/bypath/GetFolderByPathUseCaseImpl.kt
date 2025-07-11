package dev.codeswamp.articlequery.application.usecase.folder.bypath

import dev.codeswamp.articlequery.application.exception.folder.FolderNotFoundException
import dev.codeswamp.articlequery.application.readmodel.model.Folder
import dev.codeswamp.articlequery.application.readmodel.repository.FolderRepository
import org.springframework.stereotype.Service

@Service
class GetFolderByPathUseCaseImpl (
    private val folderRepository: FolderRepository,
) : GetFolderByPathUseCase{
    override suspend fun handle(query: GetFolderByPathQuery): Folder {
        return folderRepository.findFolderByFolderPath(query.path, query.fields)
            ?: throw FolderNotFoundException.byPath(query.path)
    }
}