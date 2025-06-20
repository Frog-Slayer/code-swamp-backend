package dev.codeswamp.global.image.infrastructure

import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import dev.codeswamp.global.image.application.ImageStorage
import dev.codeswamp.global.image.infrastructure.config.GcsProperties
import org.springframework.stereotype.Component
import java.util.*

@Component
class GcsImageStorage(
    private val storage: Storage,
    private val gcsProperties: GcsProperties
) : ImageStorage {

    override fun upload(contentType: String, bytes: ByteArray): String {
        val fileName = UUID.randomUUID().toString()
        val blobId = BlobId.of(gcsProperties.bucket, fileName)
        val blobInfo = BlobInfo.newBuilder(blobId)
            .setContentType(contentType)
            .build()

        storage.create(blobInfo, bytes)
        return "https://storage.googleapis.com/${gcsProperties.bucket}/$fileName"
    }

    override fun delete(imageUrl: String) {
        val blobId = BlobId.of(gcsProperties.bucket, imageUrl)
        storage.delete(blobId)
    }
}