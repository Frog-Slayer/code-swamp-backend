package dev.codeswamp.user.application.acl

interface AuthAcl {
    fun verifyTokenAndCreateAuthUser(signupToken: String, email: String) : Long
}