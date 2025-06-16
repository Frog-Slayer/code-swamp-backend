package dev.codeswamp.core.article.application.usecase.command.folder.rename

import dev.codeswamp.core.article.application.exception.folder.FolderNotFoundException
import dev.codeswamp.core.article.domain.folder.repository.FolderRepository
import dev.codeswamp.core.article.domain.folder.service.DuplicatedFolderNameChecker
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RenameFolderUseCaseImpl(
    private val folderRepository: FolderRepository,
    private val duplicatedFolderNameChecker: DuplicatedFolderNameChecker,
    private val eventPublisher: ApplicationEventPublisher,
): RenameFolderUseCase {

    @Transactional
    override fun rename(command: RenameFolderCommand) {
        val folder = folderRepository.findById(command.folderId) ?: throw FolderNotFoundException.byId(command.folderId)
        folder.checkOwnership(command.userId)

        val renamed = folder.rename(command.newName,  duplicatedFolderNameChecker::checkDuplicatedFolderNameInSameLevel)

        folderRepository.save(renamed)

        renamed.pullEvents().forEach(eventPublisher::publishEvent)
    }
}