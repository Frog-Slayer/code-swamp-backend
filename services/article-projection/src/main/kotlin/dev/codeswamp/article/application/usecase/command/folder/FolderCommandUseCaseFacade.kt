package dev.codeswamp.article.application.usecase.command.folder

import dev.codeswamp.article.application.usecase.command.folder.create.CreateFolderCommand
import dev.codeswamp.article.application.usecase.command.folder.create.CreateFolderResult
import dev.codeswamp.article.application.usecase.command.folder.create.CreateFolderUseCase
import dev.codeswamp.article.application.usecase.command.folder.create.CreateRootFolderCommand
import dev.codeswamp.article.application.usecase.command.folder.delete.DeleteFolderCommand
import dev.codeswamp.article.application.usecase.command.folder.delete.DeleteFolderUseCase
import dev.codeswamp.article.application.usecase.command.folder.move.MoveFolderCommand
import dev.codeswamp.article.application.usecase.command.folder.move.MoveFolderUseCase
import dev.codeswamp.article.application.usecase.command.folder.rename.RenameFolderCommand
import dev.codeswamp.article.application.usecase.command.folder.rename.RenameFolderUseCase
import org.springframework.stereotype.Service

@Service
class FolderCommandUseCaseFacade(
    private val createFolderUseCase: CreateFolderUseCase,
    private val moveFolderUseCase: MoveFolderUseCase,
    private val renameFolderUseCase: RenameFolderUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase,
) {

    fun create(command: CreateFolderCommand): CreateFolderResult {
        return createFolderUseCase.create(command)
    }

    fun createRoot(command: CreateRootFolderCommand) {
        createFolderUseCase.createRoot(command)
    }

    fun move(command: MoveFolderCommand) {
        moveFolderUseCase.move(command)
    }

    fun rename(command: RenameFolderCommand) {
        renameFolderUseCase.rename(command)
    }

    fun delete(command: DeleteFolderCommand) {
        deleteFolderUseCase.delete(command)
    }
}