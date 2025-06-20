package dev.codeswamp.article.application.usecase.command.folder.delete

import dev.codeswamp.article.application.cache.FolderDeletionCache
import dev.codeswamp.article.application.exception.folder.FolderNotFoundException
import dev.codeswamp.article.domain.folder.repository.FolderRepository
import dev.codeswamp.article.domain.folder.service.FolderDeletionPreparator
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import kotlin.collections.plus

@Service
class DeleteFolderUseCaseImpl(
    private val folderRepository: FolderRepository,
    private val folderDeletionPreparator: FolderDeletionPreparator,
    private val eventPublisher: ApplicationEventPublisher,
    private val deletionCache: FolderDeletionCache,
) : DeleteFolderUseCase {
    private val logger = LoggerFactory.getLogger(DeleteFolderUseCaseImpl::class.java)

    @Transactional
    override fun delete(command: DeleteFolderCommand) {
        val folder = folderRepository.findById(command.folderId) ?: throw FolderNotFoundException.Companion.byId(command.folderId)
        folder.checkOwnership(command.userId)

        val deletedAt = Instant.now()

        val (markedAsDelete, descendants) = folderDeletionPreparator.prepareDeletion(folder, deletedAt)
        val foldersToDelete = descendants + markedAsDelete.id

        folderRepository.deleteAllById(foldersToDelete)

        try {
            deletionCache.markAsDeleted(foldersToDelete, deletedAt)
        } catch (exception: Exception) {
            logger.error("Failed to update deletion cache", exception)
        }

        markedAsDelete.pullEvents().forEach(ApplicationEventPublisher::publishEvent)
    }
}