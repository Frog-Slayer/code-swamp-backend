package dev.codeswamp.articlequery.presentation.controller


import dev.codeswamp.articlequery.application.usecase.query.folder.FolderQueryUseCaseFacade
import dev.codeswamp.articlequery.application.usecase.query.folder.getuserfolders.GetAllFoldersForUserQuery
import dev.codeswamp.articlequery.presentation.dto.response.folder.GetFoldersResponse
import dev.codeswamp.authcommon.security.CustomUserDetails
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/folders")
class FolderController(
    private val folderQueryUseCaseFacade: FolderQueryUseCaseFacade
) {

    @GetMapping("/{userId}")
    suspend fun getAllFoldersForUser(@PathVariable userId: Long): ResponseEntity<GetFoldersResponse> {
        val result = folderQueryUseCaseFacade.getUserFolders(
            GetAllFoldersForUserQuery(userId)
        )

        return ResponseEntity.ok().body(
            GetFoldersResponse.Companion.from(result)
        )
    }

    @GetMapping
    suspend fun getMyFolders(@AuthenticationPrincipal user: CustomUserDetails): ResponseEntity<GetFoldersResponse> {
        val result = folderQueryUseCaseFacade.getUserFolders(
            GetAllFoldersForUserQuery(requireNotNull(user.getId()))
        )

        return ResponseEntity.ok().body(
            GetFoldersResponse.Companion.from(result)
        )
    }
}