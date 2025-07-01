package dev.codeswamp.authcommon.security

interface SkipPathProvider {
    fun skipPaths(): List<String>
}