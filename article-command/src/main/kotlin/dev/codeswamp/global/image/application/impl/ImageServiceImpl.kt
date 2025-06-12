package dev.codeswamp.global.image.application.impl

import dev.codeswamp.global.image.application.ImageService
import dev.codeswamp.global.image.application.ImageStorage
import dev.codeswamp.global.image.application.dto.UploadImageCommand
import org.springframework.stereotype.Service

@Service
class ImageServiceImpl(
    private val imageStorage: ImageStorage,
) : ImageService {
    override fun upload(uploadImageCommand: UploadImageCommand): String {
        validateContentType(uploadImageCommand.contentType)

        return imageStorage.upload(
            uploadImageCommand.contentType,
            uploadImageCommand.bytes
        )
    }

    override fun delete(imageUrl: String) {
        TODO("Not yet implemented")
    }

    private fun validateContentType(contentType: String) {
        val allowedMimeTypes = listOf(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp",
        )

        if (contentType !in allowedMimeTypes) throw IllegalArgumentException("do not support uploading $contentType")

    }

}