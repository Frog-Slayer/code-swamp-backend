package dev.codeswamp.core.application.event.outbox

import dev.codeswamp.core.common.event.BusinessEvent
import java.util.concurrent.ConcurrentHashMap

object EventTypeRegistry {
    private val registry: MutableMap<String, Class<out BusinessEvent>> = ConcurrentHashMap()

    fun register(event: BusinessEvent) : String {
        val clazz = event::class.java
        val className = clazz.simpleName

        registry.putIfAbsent(className, clazz)?. let{
            if ( it != clazz) {
                throw IllegalArgumentException("Another event type of typename $className already registered")
            }
        }

        return className
    }

    fun getClassFor(typeName: String): Class<out BusinessEvent> =
        registry[typeName] ?: throw IllegalArgumentException("Event type $typeName not registered")
}