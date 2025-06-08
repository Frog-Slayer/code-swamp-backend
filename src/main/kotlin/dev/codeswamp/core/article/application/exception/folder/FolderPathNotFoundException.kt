package dev.codeswamp.core.article.application.exception.folder

import dev.codeswamp.core.article.application.exception.application.AppNotFoundException

class FolderPathNotFoundException(
    path: String
): AppNotFoundException("cannot retrieve path $path")