package dev.codeswamp.core.user.application.acl

interface AuthServiceAcl {
    fun verifyTokenAndCreateAuthUser(signupToken: String, email: String) : Long
}