package dev.codeswamp.article.application.usecase.command.folder.create

interface CreateFolderUseCase {
    fun create(command: CreateFolderCommand): CreateFolderResult
    fun createRoot(command: CreateRootFolderCommand)
}