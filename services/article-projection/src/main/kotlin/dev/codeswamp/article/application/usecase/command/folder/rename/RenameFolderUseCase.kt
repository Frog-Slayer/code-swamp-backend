package dev.codeswamp.article.application.usecase.command.folder.rename

interface RenameFolderUseCase {
    fun rename(command: RenameFolderCommand)
}