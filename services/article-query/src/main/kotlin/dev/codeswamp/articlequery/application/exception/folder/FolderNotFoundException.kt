package dev.codeswamp.articlequery.application.exception.folder

import dev.codeswamp.articlequery.application.exception.application.AppNotFoundErrorCode
import dev.codeswamp.articlequery.application.exception.application.AppNotFoundException

class FolderNotFoundException(
    message: String,
) : AppNotFoundException(
    AppNotFoundErrorCode.FOLDER_NOT_FOUND,
    message
) {
    companion object {
        fun byId(id: Long) = FolderNotFoundException("Folder with ID $id not found")
        fun byPath(path: String) = FolderNotFoundException("Folder with path $path not found")
    }
}