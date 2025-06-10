package dev.codeswamp.core.article.presentation.controller

import dev.codeswamp.core.article.application.usecase.command.folder.FolderUseCaseFacade
import dev.codeswamp.core.article.application.usecase.command.folder.create.CreateFolderCommand
import dev.codeswamp.core.article.application.usecase.command.folder.delete.DeleteFolderCommand
import dev.codeswamp.core.article.application.usecase.command.folder.move.MoveFolderCommand
import dev.codeswamp.core.article.application.usecase.command.folder.rename.RenameFolderCommand
import dev.codeswamp.core.article.presentation.dto.request.folder.CreateFolderRequest
import dev.codeswamp.core.article.presentation.dto.request.folder.MoveFolderRequest
import dev.codeswamp.core.article.presentation.dto.request.folder.RenameFolderRequest
import dev.codeswamp.global.auth.infrastructure.security.user.CustomUserDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/folder")
class FolderController(
    private val folderUseCaseFacade: FolderUseCaseFacade
) {

    //POST createFolder
    @PostMapping
    fun create(@AuthenticationPrincipal user : CustomUserDetails, @RequestBody request: CreateFolderRequest) {
        folderUseCaseFacade.create(CreateFolderCommand(
            userId = requireNotNull(user.getId()),
            name = request.name,
            parentId = request.parentId
        ))

    }

    //PATCH rename
    @PatchMapping( "/{folderId}/rename")
    fun rename(@AuthenticationPrincipal user : CustomUserDetails, @PathVariable folderId: Long, @RequestBody request: RenameFolderRequest) {
        folderUseCaseFacade.rename(RenameFolderCommand(
            userId = requireNotNull(user.getId()),
            folderId = folderId,
            newName = request.newName
        ))
    }

    //Patch move
    @PatchMapping("/{folderId}/move")
    fun move(@AuthenticationPrincipal user : CustomUserDetails, @PathVariable folderId: Long, @RequestBody request: MoveFolderRequest) {
        folderUseCaseFacade.move(MoveFolderCommand(
            userId = requireNotNull(user.getId()),
            folderId = folderId,
            newParentId = request.newParentId
        ))
    }

    //DELETE delete
    @DeleteMapping("/{folderId}")
    fun delete(@AuthenticationPrincipal user : CustomUserDetails, @PathVariable folderId: Long) {
        folderUseCaseFacade.delete(DeleteFolderCommand(
            userId = requireNotNull(user.getId()),
            folderId = folderId
        ))

    }

}