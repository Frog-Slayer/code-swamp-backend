package dev.codeswamp.articlecommand.application.event.handler

import dev.codeswamp.articlecommand.application.event.event.UserRegisteredEvent
import dev.codeswamp.articlecommand.application.usecase.command.folder.create.CreateFolderUseCase
import dev.codeswamp.articlecommand.application.usecase.command.folder.create.CreateRootFolderCommand
import org.springframework.stereotype.Component

@Component
class UserRegisteredEventHandler(
    private val createFolderUseCase: CreateFolderUseCase,
): EventHandler<UserRegisteredEvent> {
    override fun canHandle(event: Any): Boolean = event is UserRegisteredEvent

    override suspend fun handle(event: UserRegisteredEvent) {
        createFolderUseCase.createRoot(CreateRootFolderCommand(
            name = event.username,
            userId = event.userId
        ))
    }
}