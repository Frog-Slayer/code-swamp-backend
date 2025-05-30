package dev.codeswamp.global.image.application.dto

data class UploadImageCommand (
    val userName: String,
    val contentType: String,
    val bytes: ByteArray,
)