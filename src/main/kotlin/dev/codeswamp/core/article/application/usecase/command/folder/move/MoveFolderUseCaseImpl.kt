package dev.codeswamp.core.article.application.usecase.command.folder.move

import dev.codeswamp.core.article.application.exception.folder.FolderNotFoundException
import dev.codeswamp.core.article.domain.folder.repository.FolderRepository
import dev.codeswamp.core.article.domain.folder.service.DuplicatedFolderNameChecker
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MoveFolderUseCaseImpl(
    private val folderRepository: FolderRepository,
    private val duplicatedFolderNameChecker: DuplicatedFolderNameChecker,
    private val eventPublisher: ApplicationEventPublisher
): MoveFolderUseCase {

    @Transactional
    override fun move(command: MoveFolderCommand) {
        val folder = folderRepository.findById(command.folderId) ?: throw FolderNotFoundException.byId(command.folderId)
        folder.checkOwnership(command.userId)

        val newParent = folderRepository.findById(command.newParentId) ?: throw FolderNotFoundException.byId(command.newParentId)
        newParent.checkOwnership(command.userId)

        val moved = folder.moveTo(newParent, duplicatedFolderNameChecker::checkDuplicatedFolderNameInSameLevel)

        folderRepository.save(moved)

        moved.pullEvents().forEach(eventPublisher::publishEvent)
    }
}