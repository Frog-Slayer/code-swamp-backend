package dev.codeswamp.global.auth.infrastructure.oauth2.dto

interface ProviderUserInfo {
    val email: String
    val name: String
    val profileImage: String
    val provider: String
}

