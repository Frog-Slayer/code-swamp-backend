package dev.codeswamp.global.image.infrastructure.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.Identity
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import com.google.cloud.storage.StorageRoles
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader

@Configuration
class GcsConfig(
    private val gcsProperties : GcsProperties,
    private val resourceLoader: ResourceLoader
){
    @Bean
    fun gcsStorage(): Storage {
        val bucketName = gcsProperties.bucket

        val resource = resourceLoader.getResource(gcsProperties.credentials)
        val credentials = GoogleCredentials.fromStream(resource.inputStream)

        val storage = StorageOptions.newBuilder()
            .setCredentials(credentials)
            .build()
            .service

        val policy = storage.getIamPolicy(bucketName)
                        .toBuilder()
                        .addIdentity(StorageRoles.objectViewer(), Identity.allUsers())
                        .build()

        storage.setIamPolicy(
            bucketName,
            policy
        )

        return storage
    }
}