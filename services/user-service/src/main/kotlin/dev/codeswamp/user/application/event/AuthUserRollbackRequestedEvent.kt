package dev.codeswamp.user.application.event

data class AuthUserRollbackRequestedEvent(
    val email : String
) : ApplicationEvent