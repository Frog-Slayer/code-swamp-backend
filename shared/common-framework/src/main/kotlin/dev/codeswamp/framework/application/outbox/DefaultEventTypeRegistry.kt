package dev.codeswamp.framework.application.outbox

import dev.codeswamp.core.common.EventType
import dev.codeswamp.core.common.event.BusinessEvent
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AssignableTypeFilter
import java.util.concurrent.ConcurrentHashMap

class DefaultEventTypeRegistry : EventTypeRegistry {
    private val typeToClass: MutableMap<String, Class<out BusinessEvent>> = ConcurrentHashMap()
    private val classToType: MutableMap<Class<out BusinessEvent>, String> = ConcurrentHashMap()

    @PostConstruct
    fun init() {
        val scanner = ClassPathScanningCandidateComponentProvider(false)
        scanner.addIncludeFilter(AssignableTypeFilter(BusinessEvent::class.java))

        val basePackages = listOf("dev.codeswamp")

        val eventClasses = basePackages
            .flatMap { scanner.findCandidateComponents(it) }
            .map { Class.forName(it.beanClassName) as Class<out BusinessEvent> }

        eventClasses.forEach { register(it) }
    }

    override fun register(clazz: Class<out BusinessEvent>) {
        val eventType = clazz.getAnnotation(EventType::class.java)?.value
            ?: throw IllegalArgumentException("Missing @EventType annotation on ${clazz.simpleName}")

        typeToClass.putIfAbsent(eventType, clazz)?. let{
            if ( it != clazz) {
                throw IllegalArgumentException("Another event type of typename $eventType already registered")
            }
        }

        classToType.putIfAbsent(clazz, eventType)?. let {
            if (it != eventType) {
                throw IllegalArgumentException("Another typename $eventType already registered")
            }
        }
    }

    override fun getEventTypeFor(clazz: Class<out BusinessEvent>) : String =
        classToType[clazz] ?: throw IllegalArgumentException("Cannot find eventType")

    override fun getClassFor(typeName: String): Class<out BusinessEvent> =
        typeToClass[typeName] ?: throw IllegalArgumentException("Event type $typeName not registered")
}