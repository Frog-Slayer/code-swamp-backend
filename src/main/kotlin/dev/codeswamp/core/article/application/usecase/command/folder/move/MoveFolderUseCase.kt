package dev.codeswamp.core.article.application.usecase.command.folder.move

interface MoveFolderUseCase {
    fun move(command: MoveFolderCommand)
}