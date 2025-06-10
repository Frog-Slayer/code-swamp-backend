package dev.codeswamp.core.article.application.usecase.command.folder.delete

import dev.codeswamp.core.article.application.exception.folder.FolderNotFoundException
import dev.codeswamp.core.article.domain.folder.repository.FolderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteFolderUseCaseImpl(
    private val folderRepository: FolderRepository,
) : DeleteFolderUseCase {

    @Transactional
    override fun delete(command: DeleteFolderCommand) {
        val folder = folderRepository.findById(command.folderId) ?: throw FolderNotFoundException.byId(command.folderId)
        folder.checkOwnership(command.userId)

        folderRepository.delete(folder)
    }
}