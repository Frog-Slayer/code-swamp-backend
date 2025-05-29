package dev.codeswamp.core.folder.application.service

import dev.codeswamp.core.folder.application.dto.command.CreateFolderCommand
import dev.codeswamp.core.folder.application.dto.command.CreateRootFolderCommand
import dev.codeswamp.core.folder.application.dto.command.DeleteFolderCommand
import dev.codeswamp.core.folder.application.dto.command.MoveFolderCommand
import dev.codeswamp.core.folder.domain.entity.Folder

interface FolderApplicationService {
    fun findFolderByFullPath(fullPath: String): Folder?

    //create
    fun create(createFolderCommand: CreateFolderCommand)

    fun createRoot(createRootFolderCommand: CreateRootFolderCommand)

    //moveTo
    fun move(moveFolderCommand: MoveFolderCommand)

    //delete
    fun delete(deleteFolderCommand: DeleteFolderCommand)
}