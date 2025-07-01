package dev.codeswamp.authcommon.token

interface AccessTokenParser {
    fun parse(token: String): AccessTokenPayload
}