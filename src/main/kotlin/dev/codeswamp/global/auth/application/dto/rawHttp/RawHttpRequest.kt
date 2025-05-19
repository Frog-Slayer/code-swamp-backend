package dev.codeswamp.global.auth.application.dto.rawHttp

data class RawHttpRequest (
    val headers: Map<String, String>,
    val cookies: Map<String, String>
)