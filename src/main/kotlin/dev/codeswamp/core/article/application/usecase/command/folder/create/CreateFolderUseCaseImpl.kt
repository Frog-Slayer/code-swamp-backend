package dev.codeswamp.core.article.application.usecase.command.folder.create

import dev.codeswamp.core.article.application.exception.folder.FolderNotFoundException
import dev.codeswamp.core.article.domain.folder.model.Folder
import dev.codeswamp.core.article.domain.folder.repository.FolderRepository
import dev.codeswamp.core.article.domain.folder.service.DuplicatedFolderNameChecker
import dev.codeswamp.core.article.domain.support.IdGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateFolderUseCaseImpl(
    private val folderRepository: FolderRepository,
    private val duplicatedFolderNameChecker: DuplicatedFolderNameChecker,
    private val idGenerator: IdGenerator
): CreateFolderUseCase {

    @Transactional
    override fun create(command: CreateFolderCommand) {
        val parent = folderRepository.findById(command.parentId) ?: throw FolderNotFoundException.byId(command.parentId)
        parent.checkOwnership(command.userId)

        val created = Folder.create(
            id = idGenerator.generateId(),
            ownerId = command.userId,
            name = command.name,
            parent = parent,
            checkDuplicatedFolderName = duplicatedFolderNameChecker::checkDuplicatedFolderNameInSameLevel,
        )

        folderRepository.save(created)
    }

    @Transactional
    override fun createRoot(command: CreateRootFolderCommand) {
        val rootFolder = Folder.createRoot(
            id = idGenerator.generateId(),
            name = command.name,
            ownerId = command.userId,
        )

        folderRepository.save(rootFolder)
    }
}