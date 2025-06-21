package dev.codeswamp.articlecommand.application.usecase.command.folder.create

import dev.codeswamp.articlecommand.application.exception.folder.FolderNotFoundException
import dev.codeswamp.articlecommand.domain.folder.model.Folder
import dev.codeswamp.articlecommand.domain.folder.repository.FolderRepository
import dev.codeswamp.articlecommand.domain.folder.service.DuplicatedFolderNameChecker
import dev.codeswamp.articlecommand.domain.support.IdGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateFolderUseCaseImpl(
    private val folderRepository: FolderRepository,
    private val duplicatedFolderNameChecker: DuplicatedFolderNameChecker,
    private val idGenerator: IdGenerator
) : CreateFolderUseCase {

    @Transactional
    override suspend fun create(command: CreateFolderCommand): CreateFolderResult {
        val parent = folderRepository.findById(command.parentId) ?: throw FolderNotFoundException.Companion.byId(command.parentId)
        parent.checkOwnership(command.userId)

        val created = Folder.Companion.create(
            id = idGenerator.generateId(),
            ownerId = command.userId,
            name = command.name,
            parent = parent,
            checkDuplicatedFolderName = duplicatedFolderNameChecker::checkDuplicatedFolderNameInSameLevel,
        )

        folderRepository.save(created)

        return CreateFolderResult(created.id, parent.id, created.name.value)
    }

    @Transactional
    override suspend fun createRoot(command: CreateRootFolderCommand) {
        val rootFolder = Folder.Companion.createRoot(
            id = idGenerator.generateId(),
            name = command.name,
            ownerId = command.userId,
        )

        folderRepository.save(rootFolder)
    }
}