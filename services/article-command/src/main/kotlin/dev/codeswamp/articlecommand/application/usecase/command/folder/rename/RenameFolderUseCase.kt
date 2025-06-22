package dev.codeswamp.articlecommand.application.usecase.command.folder.rename

interface RenameFolderUseCase {
    suspend fun rename(command: RenameFolderCommand)
}