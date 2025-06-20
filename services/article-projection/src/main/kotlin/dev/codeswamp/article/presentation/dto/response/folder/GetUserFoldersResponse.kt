package dev.codeswamp.article.presentation.dto.response.folder

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import dev.codeswamp.article.application.usecase.query.folder.getuserfolders.GetAllFoldersForUserResult

data class FolderInfoResponse (
    @JsonSerialize(using = ToStringSerializer::class)
    val id: Long,
    val name: String,
    @JsonSerialize(using = ToStringSerializer::class)
    val parentId: Long?
)

data class GetFoldersResponse (
    val folders : List<FolderInfoResponse>
){
    companion object {
        fun from(result : GetAllFoldersForUserResult) : GetFoldersResponse {
            val folders = result.folders
            return GetFoldersResponse( folders.map {
                    FolderInfoResponse(
                        id= it.id,
                        name = it.name,
                        parentId = it.parentId
                    )
                }
            )
        }
    }
}