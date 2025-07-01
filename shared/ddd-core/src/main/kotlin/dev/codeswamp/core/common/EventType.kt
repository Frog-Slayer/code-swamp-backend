package dev.codeswamp.core.common

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class EventType(val value: String)
