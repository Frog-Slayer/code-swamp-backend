package dev.codeswamp.article.application.usecase.command.folder.rename

import dev.codeswamp.article.application.exception.folder.FolderNotFoundException
import dev.codeswamp.article.domain.folder.repository.FolderRepository
import dev.codeswamp.article.domain.folder.service.DuplicatedFolderNameChecker
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
        val folder = folderRepository.findById(command.folderId) ?: throw FolderNotFoundException.Companion.byId(command.folderId)
        folder.checkOwnership(command.userId)

        val renamed = folder.rename(command.newName,  duplicatedFolderNameChecker::checkDuplicatedFolderNameInSameLevel)

        folderRepository.save(renamed)

        renamed.pullEvents().forEach(ApplicationEventPublisher::publishEvent)
    }
}