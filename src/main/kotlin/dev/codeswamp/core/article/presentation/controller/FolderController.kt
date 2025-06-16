package dev.codeswamp.core.article.presentation.controller

import dev.codeswamp.core.article.application.usecase.command.folder.FolderCommandUseCaseFacade
import dev.codeswamp.core.article.application.usecase.command.folder.create.CreateFolderCommand
import dev.codeswamp.core.article.application.usecase.command.folder.delete.DeleteFolderCommand
import dev.codeswamp.core.article.application.usecase.command.folder.move.MoveFolderCommand
import dev.codeswamp.core.article.application.usecase.command.folder.rename.RenameFolderCommand
import dev.codeswamp.core.article.application.usecase.query.folder.FolderQueryUseCaseFacade
import dev.codeswamp.core.article.application.usecase.query.folder.getuserfolders.GetAllFoldersForUserQuery
import dev.codeswamp.core.article.presentation.dto.request.folder.CreateFolderRequest
import dev.codeswamp.core.article.presentation.dto.request.folder.MoveFolderRequest
import dev.codeswamp.core.article.presentation.dto.request.folder.RenameFolderRequest
import dev.codeswamp.core.article.presentation.dto.response.SimpleResponse
import dev.codeswamp.core.article.presentation.dto.response.folder.FolderInfoResponse
import dev.codeswamp.core.article.presentation.dto.response.folder.GetFoldersResponse
import dev.codeswamp.global.auth.infrastructure.security.user.CustomUserDetails
import org.springframework.data.redis.core.query.QueryUtils
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/folders")
class FolderController(
    private val folderCommandUseCaseFacade: FolderCommandUseCaseFacade,
    private val folderQueryUseCaseFacade: FolderQueryUseCaseFacade
) {

    @PostMapping
    fun create(
        @AuthenticationPrincipal user : CustomUserDetails,
        @RequestBody request: CreateFolderRequest
    ) : ResponseEntity<SimpleResponse> {
        val createResult = folderCommandUseCaseFacade.create(CreateFolderCommand(
            userId = requireNotNull(user.getId()),
            name = request.name,
            parentId = request.parentId
        ))

        return ResponseEntity
            .created(URI("/folders/${createResult.folderId}"))
            .body(SimpleResponse("created folder successfully"))
    }

    @PatchMapping( "/{folderId}/rename")
    fun rename(
        @AuthenticationPrincipal user : CustomUserDetails,
        @PathVariable folderId: Long,
        @RequestBody request: RenameFolderRequest)
    : ResponseEntity<SimpleResponse>
    {
        folderCommandUseCaseFacade.rename(RenameFolderCommand(
            userId = requireNotNull(user.getId()),
            folderId = folderId,
            newName = request.newName
        ))

        return ResponseEntity.ok()
            .body(SimpleResponse("renamed folder successfully"))
    }

    @PatchMapping("/{folderId}/move")
    fun move(
        @AuthenticationPrincipal user : CustomUserDetails,
        @PathVariable folderId: Long,
        @RequestBody request: MoveFolderRequest)
    : ResponseEntity<SimpleResponse>{
        folderCommandUseCaseFacade.move(MoveFolderCommand(
            userId = requireNotNull(user.getId()),
            folderId = folderId,
            newParentId = request.newParentId
        ))

        return ResponseEntity.ok()
            .body(SimpleResponse("moved folder successfully"))
    }

    @DeleteMapping("/{folderId}")
    fun delete(
        @AuthenticationPrincipal user : CustomUserDetails,
        @PathVariable folderId: Long)
    : ResponseEntity<SimpleResponse> {
        folderCommandUseCaseFacade.delete(DeleteFolderCommand(
            userId = requireNotNull(user.getId()),
            folderId = folderId
        ))

        return ResponseEntity.ok()
            .body(SimpleResponse("deleted folder successfully"))
    }

    @GetMapping("/{userId}")
    fun getAllFoldersForUser(@PathVariable  userId: Long): ResponseEntity<GetFoldersResponse> {
        val result = folderQueryUseCaseFacade.getUserFolders(
            GetAllFoldersForUserQuery(userId)
        )

        return ResponseEntity.ok().body(
            GetFoldersResponse.from(result)
        )
    }

     @GetMapping
    fun getMyFolders(@AuthenticationPrincipal user : CustomUserDetails): ResponseEntity<GetFoldersResponse> {
        val result = folderQueryUseCaseFacade.getUserFolders(
            GetAllFoldersForUserQuery(requireNotNull(user.getId()))
        )

        return ResponseEntity.ok().body(
            GetFoldersResponse.from(result)
        )
    }
}