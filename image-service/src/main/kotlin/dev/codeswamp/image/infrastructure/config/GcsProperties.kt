package dev.codeswamp.image.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "gcs")
class GcsProperties {
    lateinit var bucket: String
    lateinit var credentials: String
}
