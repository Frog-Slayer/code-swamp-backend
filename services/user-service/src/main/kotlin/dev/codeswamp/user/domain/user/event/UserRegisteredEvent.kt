package dev.codeswamp.user.domain.user.event

import dev.codeswamp.user.domain.user.DomainEvent

data class UserRegisteredEvent(
    val userId: Long,
    val username: String,
) : DomainEvent