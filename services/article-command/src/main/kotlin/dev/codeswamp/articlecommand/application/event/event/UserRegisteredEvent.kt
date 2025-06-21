package dev.codeswamp.articlecommand.application.event.event

import dev.codeswamp.core.application.event.ApplicationEvent

data class UserRegisteredEvent(
    val userId: Long,
    val username: String,
)  : ApplicationEvent