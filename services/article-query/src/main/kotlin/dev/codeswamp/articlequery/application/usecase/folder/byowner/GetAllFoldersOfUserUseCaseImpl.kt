package dev.codeswamp.articlequery.application.usecase.folder.byowner

import dev.codeswamp.articlequery.application.readmodel.model.Folder
import dev.codeswamp.articlequery.application.readmodel.repository.FolderRepository
import org.springframework.stereotype.Service

@Service
class GetAllFoldersOfUserUseCaseImpl(
    private val folderRepository: FolderRepository,
) : GetAllFoldersOfUserUseCase {
    override suspend fun handle(query: GetAllFoldersOfUserQuery) : List<Folder> {
        return folderRepository.findAllByOwnerId(query.ownerId, query.fields)
    }
}