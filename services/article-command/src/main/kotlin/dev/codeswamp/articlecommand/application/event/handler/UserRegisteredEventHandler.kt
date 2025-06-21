package dev.codeswamp.articlecommand.application.event.handler

import dev.codeswamp.articlecommand.application.event.event.UserRegisteredEvent
import dev.codeswamp.articlecommand.application.usecase.command.folder.create.CreateFolderUseCase
import dev.codeswamp.articlecommand.application.usecase.command.folder.create.CreateRootFolderCommand
import dev.codeswamp.core.common.event.Event
import dev.codeswamp.core.common.event.EventHandler
import org.springframework.stereotype.Component

@Component
class UserRegisteredEventHandler(
    private val createFolderUseCase: CreateFolderUseCase,
) : EventHandler<UserRegisteredEvent> {
    override fun canHandle(event: Event): Boolean = event is UserRegisteredEvent

    override suspend fun handle(event: UserRegisteredEvent) {
        createFolderUseCase.createRoot(
            CreateRootFolderCommand(
                name = event.username,
                userId = event.userId
            )
        )
    }
}