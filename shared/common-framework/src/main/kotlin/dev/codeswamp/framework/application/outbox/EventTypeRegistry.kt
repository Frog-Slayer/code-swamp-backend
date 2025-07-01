package dev.codeswamp.framework.application.outbox

import dev.codeswamp.core.common.event.BusinessEvent

interface EventTypeRegistry  {
    fun register(clazz: Class<out BusinessEvent>)
    fun getEventTypeFor(clazz: Class<out BusinessEvent>): String
    fun getClassFor(typeName: String): Class<out BusinessEvent>
}