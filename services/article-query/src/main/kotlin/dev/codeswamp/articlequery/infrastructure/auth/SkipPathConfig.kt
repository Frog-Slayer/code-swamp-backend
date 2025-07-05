package dev.codeswamp.articlequery.infrastructure.auth

import dev.codeswamp.authcommon.security.DefaultSecurityConfig
import dev.codeswamp.authcommon.security.SkipPathProvider
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(DefaultSecurityConfig::class)
class SkipPathConfig : SkipPathProvider {
    override fun skipPaths(): List<String> {
        return listOf("/articles/recent")
    }
}