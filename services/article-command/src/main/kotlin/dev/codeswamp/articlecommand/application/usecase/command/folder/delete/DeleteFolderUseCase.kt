package dev.codeswamp.articlecommand.application.usecase.command.folder.delete

interface DeleteFolderUseCase {
    suspend fun delete(command: DeleteFolderCommand)
}