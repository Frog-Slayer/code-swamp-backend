package dev.codeswamp.articlequery.presentation.dto.response

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import dev.codeswamp.articlequery.application.readmodel.model.Folder

data class FolderResponse private constructor(
    @JsonSerialize(using = ToStringSerializer::class)
    val id: Long?,

    @JsonSerialize(using = ToStringSerializer::class)
    val ownerId: Long?,
    val name: String?,
    val fullPath: String?,

    @JsonSerialize(using = ToStringSerializer::class)
    val parentId: Long?
) {
    companion object {
        fun from(folder: Folder?) = FolderResponse(
            id = folder?.id,
            ownerId = folder?.ownerId,
            name = folder?.name,
            fullPath =  folder?.fullPath,
            parentId = folder?.parentId
        )
    }
}