package dev.codeswamp.auth.presentation.dto

data class TemporaryLoginRequest (
    val email: String,
    val token: String
)