package dev.codeswamp.image.application

interface ImageStorage {
    fun upload(contentType: String, bytes: ByteArray): String
    fun delete(imageUrl: String)
}