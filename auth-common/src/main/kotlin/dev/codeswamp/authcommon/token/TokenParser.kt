package dev.codeswamp.authcommon.token

interface TokenParser {
    fun parse(token: String): TokenPayload
}