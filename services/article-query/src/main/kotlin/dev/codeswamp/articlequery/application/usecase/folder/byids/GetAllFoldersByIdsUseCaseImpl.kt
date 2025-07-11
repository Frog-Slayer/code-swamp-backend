package dev.codeswamp.articlequery.application.usecase.folder.byids

import dev.codeswamp.articlequery.application.readmodel.model.Folder
import dev.codeswamp.articlequery.application.readmodel.repository.FolderRepository
import org.springframework.stereotype.Service

@Service
class GetAllFoldersByIdsUseCaseImpl (
    private val folderRepository: FolderRepository,
) : GetAllFoldersByIdsUseCase {
    override suspend fun handle(query: GetAllFoldersByIdsQuery): List<Folder> {
        return folderRepository.findAllByIds(query.folderIds, query.fields)
    }
}