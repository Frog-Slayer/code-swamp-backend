package dev.codeswamp.article.application.usecase.command.folder.move

import dev.codeswamp.article.application.exception.folder.FolderNotFoundException
import dev.codeswamp.article.domain.folder.repository.FolderRepository
import dev.codeswamp.article.domain.folder.service.DuplicatedFolderNameChecker
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MoveFolderUseCaseImpl(
    private val folderRepository: FolderRepository,
    private val duplicatedFolderNameChecker: DuplicatedFolderNameChecker,
    private val eventPublisher: ApplicationEventPublisher,
) : MoveFolderUseCase {

    @Transactional
    override fun move(command: MoveFolderCommand) {
        val folder = folderRepository.findById(command.folderId) ?: throw FolderNotFoundException.Companion.byId(command.folderId)
        folder.checkOwnership(command.userId)

        val newParent = folderRepository.findById(command.newParentId) ?: throw FolderNotFoundException.Companion.byId(command.newParentId)
        newParent.checkOwnership(command.userId)

        val moved = folder.moveTo(newParent, duplicatedFolderNameChecker::checkDuplicatedFolderNameInSameLevel)

        folderRepository.save(moved)

        moved.pullEvents().forEach(ApplicationEventPublisher::publishEvent)
    }
}