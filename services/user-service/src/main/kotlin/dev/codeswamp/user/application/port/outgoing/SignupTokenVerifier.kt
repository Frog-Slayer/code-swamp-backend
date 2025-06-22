package dev.codeswamp.user.application.port.outgoing

data class VerificationResult(
    val userId: Long,
    val otp: String
)

interface SignupTokenVerifier {
    suspend fun verifyTokenAndCreateUser(signupToken: String, email: String): VerificationResult
}