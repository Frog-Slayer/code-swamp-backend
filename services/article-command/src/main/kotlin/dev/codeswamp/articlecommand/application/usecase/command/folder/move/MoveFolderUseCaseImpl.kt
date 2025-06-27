package dev.codeswamp.articlecommand.application.usecase.command.folder.move

import dev.codeswamp.articlecommand.application.exception.folder.FolderNotFoundException
import dev.codeswamp.articlecommand.domain.folder.repository.FolderRepository
import dev.codeswamp.articlecommand.domain.folder.service.DuplicatedFolderNameChecker
import dev.codeswamp.core.application.event.eventbus.EventRecorder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MoveFolderUseCaseImpl(
    private val folderRepository: FolderRepository,
    private val duplicatedFolderNameChecker: DuplicatedFolderNameChecker,
    private val eventRecorder : EventRecorder,
) : MoveFolderUseCase {

    @Transactional
    override suspend fun move(command: MoveFolderCommand) {
        val folder = folderRepository.findById(command.folderId) ?: throw FolderNotFoundException.Companion.byId(command.folderId)
        folder.checkOwnership(command.userId)

        val newParent = folderRepository.findById(command.newParentId) ?: throw FolderNotFoundException.Companion.byId(command.newParentId)
        newParent.checkOwnership(command.userId)

        val moved = folder.moveTo(newParent, duplicatedFolderNameChecker::checkDuplicatedFolderNameInSameLevel)

        folderRepository.save(moved)


        eventRecorder.recordAll(moved.pullEvents() )
    }
}