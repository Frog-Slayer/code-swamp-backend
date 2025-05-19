package dev.codeswamp.global.auth.application.dto.rawHttp

data class RawHttpResponse (
    val cookies: MutableMap<String, String>
)