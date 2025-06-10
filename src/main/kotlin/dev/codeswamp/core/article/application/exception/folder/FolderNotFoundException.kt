package dev.codeswamp.core.article.application.exception.folder

import dev.codeswamp.core.article.application.exception.application.AppNotFoundException

class FolderNotFoundException(
    message: String,
) : AppNotFoundException(message) {
    companion object {
        fun byId(id: Long) = FolderNotFoundException("Folder with ID $id not found")
        fun byPath(path: String) = FolderNotFoundException("Folder with path $path not found")
    }
}