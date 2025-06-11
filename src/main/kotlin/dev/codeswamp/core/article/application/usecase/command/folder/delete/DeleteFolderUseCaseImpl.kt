package dev.codeswamp.core.article.application.usecase.command.folder.delete

import dev.codeswamp.core.article.application.exception.folder.FolderNotFoundException
import dev.codeswamp.core.article.domain.folder.repository.FolderRepository
import dev.codeswamp.core.article.domain.folder.service.FolderDeletionService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteFolderUseCaseImpl(
    private val folderRepository: FolderRepository,
    private val folderDeletionService: FolderDeletionService,
    private val eventPublisher: ApplicationEventPublisher,
) : DeleteFolderUseCase {

    @Transactional
    override fun delete(command: DeleteFolderCommand) {
        val folder = folderRepository.findById(command.folderId) ?: throw FolderNotFoundException.byId(command.folderId)
        folder.checkOwnership(command.userId)

        val markedAsDelete = folderDeletionService.markFolderAndDescendantsAsDeleted(folder)

        val events = markedAsDelete.pullEvents()

        folderRepository.deleteAllByIdsIn(events)
        deletionCache.




        events.forEach(eventPublisher::publishEvent)
    }
}