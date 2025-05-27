package dev.codeswamp.core.folder.application.service

import dev.codeswamp.core.folder.application.dto.command.CreateFolderCommand

interface FolderApplicationService {
    //findByUserId

    //create
    fun create(createFolderCommand: CreateFolderCommand)

    //rename

    //moveTo

    //delete
}