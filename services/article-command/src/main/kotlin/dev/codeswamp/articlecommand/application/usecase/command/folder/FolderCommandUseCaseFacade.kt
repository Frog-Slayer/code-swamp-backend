package dev.codeswamp.articlecommand.application.usecase.command.folder

import dev.codeswamp.articlecommand.application.usecase.command.folder.create.CreateFolderCommand
import dev.codeswamp.articlecommand.application.usecase.command.folder.create.CreateFolderResult
import dev.codeswamp.articlecommand.application.usecase.command.folder.create.CreateFolderUseCase
import dev.codeswamp.articlecommand.application.usecase.command.folder.create.CreateRootFolderCommand
import dev.codeswamp.articlecommand.application.usecase.command.folder.delete.DeleteFolderCommand
import dev.codeswamp.articlecommand.application.usecase.command.folder.delete.DeleteFolderUseCase
import dev.codeswamp.articlecommand.application.usecase.command.folder.move.MoveFolderCommand
import dev.codeswamp.articlecommand.application.usecase.command.folder.move.MoveFolderUseCase
import dev.codeswamp.articlecommand.application.usecase.command.folder.rename.RenameFolderCommand
import dev.codeswamp.articlecommand.application.usecase.command.folder.rename.RenameFolderUseCase
import org.springframework.stereotype.Service

@Service
class FolderCommandUseCaseFacade(
    private val createFolderUseCase: CreateFolderUseCase,
    private val moveFolderUseCase: MoveFolderUseCase,
    private val renameFolderUseCase: RenameFolderUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase,
) {

    suspend fun create(command: CreateFolderCommand): CreateFolderResult {
        return createFolderUseCase.create(command)
    }

    suspend fun createRoot(command: CreateRootFolderCommand) {
        createFolderUseCase.createRoot(command)
    }

    suspend fun move(command: MoveFolderCommand) {
        moveFolderUseCase.move(command)
    }

    suspend fun rename(command: RenameFolderCommand) {
        renameFolderUseCase.rename(command)
    }

    suspend fun delete(command: DeleteFolderCommand) {
        deleteFolderUseCase.delete(command)
    }
}