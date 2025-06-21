package dev.codeswamp.articlecommand.application.event.event

data class UserRegisteredEvent(
    val userId: Long,
    val username: String,
)  : ApplicationEvent