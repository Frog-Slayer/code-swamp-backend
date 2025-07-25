package dev.codeswamp.articlecommand.application.usecase.command.folder.rename

import dev.codeswamp.articlecommand.application.exception.folder.FolderNotFoundException
import dev.codeswamp.articlecommand.domain.folder.repository.FolderRepository
import dev.codeswamp.articlecommand.domain.folder.service.DuplicatedFolderNameChecker
import dev.codeswamp.framework.application.outbox.EventRecorder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RenameFolderUseCaseImpl(
    private val folderRepository: FolderRepository,
    private val duplicatedFolderNameChecker: DuplicatedFolderNameChecker,
    private val eventRecorder: EventRecorder,
) : RenameFolderUseCase {

    @Transactional
    override suspend fun rename(command: RenameFolderCommand) {
        val folder = folderRepository.findById(command.folderId) ?: throw FolderNotFoundException.Companion.byId(command.folderId)
        folder.checkOwnership(command.userId)

        val renamed = folder.rename(command.newName, duplicatedFolderNameChecker::checkDuplicatedFolderNameInSameLevel)

        folderRepository.save(renamed)

        eventRecorder.recordAll(renamed.pullEvents() )
    }
}