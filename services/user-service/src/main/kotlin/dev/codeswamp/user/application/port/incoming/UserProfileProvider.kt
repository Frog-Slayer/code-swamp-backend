package dev.codeswamp.user.application.port.incoming

data class UserProfile (
    val username: String,
    val profileImage: String,
)

interface UserProfileProvider {
    fun provideUserProfile(userId : Long) : UserProfile
}