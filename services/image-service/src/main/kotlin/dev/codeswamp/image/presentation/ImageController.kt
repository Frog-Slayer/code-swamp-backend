package dev.codeswamp.image.presentation

import dev.codeswamp.image.application.ImageService
import dev.codeswamp.image.presentation.dto.ImageUploadResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/images")
class ImageController(
    private val imageService: ImageService,
) {
    @PostMapping("/upload")
    fun uploadImage(@RequestParam("image") file: MultipartFile): ImageUploadResponse {
        TODO()

        /**
        val uploadImageCommand = UploadImageCommand(
        userName = user?.username ?: throw AccessDeniedException("do not have authority"),
        contentType = file.contentType ?: throw IllegalArgumentException("file must have file contentType"),
        bytes = file.bytes,
        )

        val imageUrl = imageService.upload(uploadImageCommand);
        return ImageUploadResponse(imageUrl)
         */
    }
}