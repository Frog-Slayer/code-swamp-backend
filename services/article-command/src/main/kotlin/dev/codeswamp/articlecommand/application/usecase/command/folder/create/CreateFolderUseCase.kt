package dev.codeswamp.articlecommand.application.usecase.command.folder.create

interface CreateFolderUseCase {
    suspend fun create(command: CreateFolderCommand): CreateFolderResult
    suspend fun createRoot(command: CreateRootFolderCommand)
}