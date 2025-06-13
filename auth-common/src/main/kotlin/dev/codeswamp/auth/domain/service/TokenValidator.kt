package dev.codeswamp.auth.domain.service

import dev.codeswamp.auth.domain.model.AuthUser
import dev.codeswamp.auth.domain.model.Role
import dev.codeswamp.auth.domain.model.token.RawAccessToken
import dev.codeswamp.auth.domain.model.token.ValidatedAccessToken

class TokenValidator {

    fun validate(rawAccessToken : RawAccessToken) : ValidatedAccessToken{
        if (rawAccessToken.isExpired()) throw RuntimeException("Token expired!")

        val authUser = AuthUser(
            id = rawAccessToken.userId,
            username = rawAccessToken.username,
            role = Role.USER//TODO
        )

        return ValidatedAccessToken(rawAccessToken.value, authUser, rawAccessToken.expiration)
    }
}