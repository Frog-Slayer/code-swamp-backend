package dev.codeswamp.articlecommand.application.usecase.command.folder.delete

import dev.codeswamp.articlecommand.application.exception.folder.FolderNotFoundException
import dev.codeswamp.articlecommand.domain.folder.repository.FolderRepository
import dev.codeswamp.articlecommand.domain.folder.service.FolderDeletionPreparator
import dev.codeswamp.framework.application.outbox.EventRecorder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class DeleteFolderUseCaseImpl(
    private val folderRepository: FolderRepository,
    private val folderDeletionPreparator: FolderDeletionPreparator,
    private val eventRecorder: EventRecorder,
) : DeleteFolderUseCase {
    private val logger = LoggerFactory.getLogger(DeleteFolderUseCaseImpl::class.java)

    @Transactional
    override suspend fun delete(command: DeleteFolderCommand) {
        val folder = folderRepository.findById(command.folderId) ?: throw FolderNotFoundException.Companion.byId(command.folderId)
        folder.checkOwnership(command.userId)

        val deletedAt = Instant.now()

        val (markedAsDelete, descendants) = folderDeletionPreparator.prepareDeletion(folder, deletedAt)
        val foldersToDelete = descendants + markedAsDelete.id

        folderRepository.deleteAllById(foldersToDelete)

        eventRecorder.recordAll(markedAsDelete.pullEvents())
    }
}