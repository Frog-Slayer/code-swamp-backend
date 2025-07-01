package dev.codeswamp.user.infrastructure.auth

import dev.codeswamp.authcommon.security.DefaultSecurityConfig
import dev.codeswamp.authcommon.security.SkipPathProvider
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(DefaultSecurityConfig::class)
@Configuration
class SkipPathConfig :  SkipPathProvider{
    override fun skipPaths(): List<String> {
        return listOf(
            "/signup"
        )
    }
}