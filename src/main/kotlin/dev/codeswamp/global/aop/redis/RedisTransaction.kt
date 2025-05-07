package dev.codeswamp.global.aop.redis

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisTransaction()