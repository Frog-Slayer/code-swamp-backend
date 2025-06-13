package dev.codeswamp.global.authgateway.presentation.dto

data class TemporaryLoginRequest (
    val email: String,
    val token: String
)