package dev.codeswamp.global.image.presentation

import dev.codeswamp.global.auth.infrastructure.security.user.CustomUserDetails
import dev.codeswamp.global.image.application.ImageService
import dev.codeswamp.global.image.application.dto.UploadImageCommand
import dev.codeswamp.global.image.presentation.dto.ImageUploadResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/images")
class ImageController(
    private val imageService: ImageService,
){
    @PostMapping("/upload")
    fun uploadImage(@AuthenticationPrincipal user: CustomUserDetails?, @RequestParam("image") file: MultipartFile) : ImageUploadResponse {
        val uploadImageCommand = UploadImageCommand(
            userName = user?.username ?: throw AccessDeniedException("do not have authority"),
            contentType = file.contentType ?: throw IllegalArgumentException("file must have file contentType"),
            bytes = file.bytes,
        )

        val imageUrl = imageService.upload(uploadImageCommand);
        return ImageUploadResponse(imageUrl)
    }
}