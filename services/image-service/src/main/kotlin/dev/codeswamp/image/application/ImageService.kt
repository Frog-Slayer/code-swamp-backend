package dev.codeswamp.image.application

import dev.codeswamp.image.application.dto.UploadImageCommand

interface ImageService {
    fun upload(uploadImageCommand: UploadImageCommand): String

    fun delete(imageUrl: String)
}