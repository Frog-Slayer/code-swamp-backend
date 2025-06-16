package dev.codeswamp.global.image.application

import dev.codeswamp.global.image.application.dto.UploadImageCommand

interface ImageService {
    fun upload(uploadImageCommand: UploadImageCommand) : String

    fun delete(imageUrl: String)
}