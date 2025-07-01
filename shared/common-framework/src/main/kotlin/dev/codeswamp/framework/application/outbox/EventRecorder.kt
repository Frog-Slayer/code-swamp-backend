package dev.codeswamp.framework.application.outbox

import dev.codeswamp.core.common.event.BusinessEvent

interface EventRecorder {
    suspend fun record(event: BusinessEvent)
    suspend fun recordAll(events: List<BusinessEvent>)
}