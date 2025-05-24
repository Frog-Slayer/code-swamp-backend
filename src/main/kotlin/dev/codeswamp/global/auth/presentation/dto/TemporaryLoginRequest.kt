package dev.codeswamp.global.auth.presentation.dto

data class TemporaryLoginRequest (
    val email: String,
    val token: String
)