package dev.codeswamp.global.auth.application.dto

data class RawHttpResponse (
    val cookies: MutableMap<String, String>
)
