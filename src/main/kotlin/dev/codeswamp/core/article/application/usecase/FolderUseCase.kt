package dev.codeswamp.core.article.application.usecase

import dev.codeswamp.core.article.application.dto.command.CreateFolderCommand
import dev.codeswamp.core.article.application.dto.command.CreateRootFolderCommand
import dev.codeswamp.core.article.application.dto.command.DeleteFolderCommand
import dev.codeswamp.core.article.application.dto.command.MoveFolderCommand
import dev.codeswamp.core.article.domain.folder.model.Folder

interface FolderUseCase {
    fun findFolderByFullPath(fullPath: String): Folder?

    //create
    fun create(createFolderCommand: CreateFolderCommand)

    fun createRoot(createRootFolderCommand: CreateRootFolderCommand)

    //moveTo
    fun move(moveFolderCommand: MoveFolderCommand)

    //delete
    fun delete(deleteFolderCommand: DeleteFolderCommand)
}