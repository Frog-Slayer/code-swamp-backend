package dev.codeswamp.user.application.port.outgoing

interface SignupTokenVerifier {
    fun verifyTokenAndCreateUser(signupToken: String, email: String) : Long
}