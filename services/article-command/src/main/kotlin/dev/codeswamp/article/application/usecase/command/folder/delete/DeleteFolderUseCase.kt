package dev.codeswamp.article.application.usecase.command.folder.delete

interface DeleteFolderUseCase {
    fun delete(command: DeleteFolderCommand)
}