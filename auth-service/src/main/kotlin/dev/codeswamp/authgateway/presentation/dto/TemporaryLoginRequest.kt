package dev.codeswamp.authgateway.presentation.dto

data class TemporaryLoginRequest (
    val email: String,
    val token: String
)