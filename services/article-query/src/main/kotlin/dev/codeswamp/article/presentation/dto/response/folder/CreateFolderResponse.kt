package dev.codeswamp.article.presentation.dto.response.folder

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import dev.codeswamp.article.application.usecase.command.folder.create.CreateFolderResult

data class CreateFolderResponse(
    @JsonSerialize(using = ToStringSerializer::class)
    val folderId: Long,

    @JsonSerialize(using = ToStringSerializer::class)
    val parentId: Long,
    val name: String,
) {
    companion object {
        fun from(result: CreateFolderResult) = CreateFolderResponse(
            folderId = result.folderId,
            parentId = result.parentId,
            name = result.name,
        )
    }
}