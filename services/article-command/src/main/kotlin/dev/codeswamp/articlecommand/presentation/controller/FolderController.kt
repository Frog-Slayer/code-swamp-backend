package dev.codeswamp.articlecommand.presentation.controller

import dev.codeswamp.articlecommand.application.usecase.command.folder.FolderCommandUseCaseFacade
import dev.codeswamp.articlecommand.application.usecase.command.folder.create.CreateFolderCommand
import dev.codeswamp.articlecommand.application.usecase.command.folder.delete.DeleteFolderCommand
import dev.codeswamp.articlecommand.application.usecase.command.folder.move.MoveFolderCommand
import dev.codeswamp.articlecommand.application.usecase.command.folder.rename.RenameFolderCommand
import dev.codeswamp.articlecommand.presentation.dto.request.folder.CreateFolderRequest
import dev.codeswamp.articlecommand.presentation.dto.request.folder.MoveFolderRequest
import dev.codeswamp.articlecommand.presentation.dto.request.folder.RenameFolderRequest
import dev.codeswamp.articlecommand.presentation.dto.response.SimpleResponse
import dev.codeswamp.articlecommand.presentation.dto.response.folder.CreateFolderResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/folders")
class FolderController(
    private val folderCommandUseCaseFacade: FolderCommandUseCaseFacade,
) {

    @PostMapping
    suspend fun create(
        @RequestAttribute userId: Long,
        @RequestBody request: CreateFolderRequest
    ): ResponseEntity<CreateFolderResponse> {

        val createResult = folderCommandUseCaseFacade.create(
            CreateFolderCommand(
                userId = requireNotNull(userId),
                name = request.name,
                parentId = request.parentId
            )
        )

        return ResponseEntity
            .created(URI("/folders/${createResult.folderId}"))
            .body(CreateFolderResponse.Companion.from(createResult))
    }

    @PatchMapping("/{folderId}/rename")
    suspend fun rename(
        @RequestAttribute userId: Long,
        @PathVariable folderId: Long,
        @RequestBody request: RenameFolderRequest
    )
            : ResponseEntity<SimpleResponse> {
        folderCommandUseCaseFacade.rename(
            RenameFolderCommand(
                userId = requireNotNull(userId),
                folderId = folderId,
                newName = request.newName
            )
        )

        return ResponseEntity.ok()
            .body(SimpleResponse("renamed folder successfully"))
    }

    @PatchMapping("/{folderId}/move")
    suspend fun move(
        @RequestAttribute userId: Long,
        @PathVariable folderId: Long,
        @RequestBody request: MoveFolderRequest
    )
            : ResponseEntity<SimpleResponse> {
        folderCommandUseCaseFacade.move(
            MoveFolderCommand(
                userId = requireNotNull(userId),
                folderId = folderId,
                newParentId = request.newParentId
            )
        )

        return ResponseEntity.ok()
            .body(SimpleResponse("moved folder successfully"))
    }

    @DeleteMapping("/{folderId}")
    suspend fun delete(
        @RequestAttribute userId: Long,
        @PathVariable folderId: Long
    )
            : ResponseEntity<SimpleResponse> {
        folderCommandUseCaseFacade.delete(
            DeleteFolderCommand(
                userId = requireNotNull(userId),
                folderId = folderId
            )
        )

        return ResponseEntity.ok()
            .body(SimpleResponse("deleted folder successfully"))
    }
}