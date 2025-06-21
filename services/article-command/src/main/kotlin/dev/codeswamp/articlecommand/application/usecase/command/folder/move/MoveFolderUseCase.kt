package dev.codeswamp.articlecommand.application.usecase.command.folder.move

interface MoveFolderUseCase {
    suspend fun move(command: MoveFolderCommand)
}