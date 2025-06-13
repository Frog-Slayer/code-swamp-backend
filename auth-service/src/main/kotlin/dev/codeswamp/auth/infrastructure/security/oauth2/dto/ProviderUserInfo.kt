package dev.codeswamp.auth.infrastructure.security.oauth2.dto

interface ProviderUserInfo {
    val email: String
    val name: String
    val profileImage: String
    val provider: String
}

