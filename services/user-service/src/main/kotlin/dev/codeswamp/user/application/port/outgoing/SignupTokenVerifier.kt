package dev.codeswamp.user.application.port.outgoing

interface SignupTokenVerifier {
    suspend fun verifyTokenAndCreateUser(signupToken: String, email: String) : Long
}