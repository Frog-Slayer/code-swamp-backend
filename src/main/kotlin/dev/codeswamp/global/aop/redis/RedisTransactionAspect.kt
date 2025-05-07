package dev.codeswamp.global.aop.redis

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Aspect
@Component
class RedisTransactionAspect (
    private val redisTemplate: RedisTemplate<String, Any>
) {
    @Pointcut("@annotation(dev.codeswamp.global.aop.redis.TransactionalRedis)")
    fun transactionalMethod() {}

    @Around("transactionalMethod()")
    fun aroundTransaction(joinPoint: ProceedingJoinPoint): Any? {
        val connection = redisTemplate.connectionFactory?.connection
        return try {
            connection?.multi()
            val result = joinPoint.proceed()
            connection?.exec()
            result //joinPoint.proceed()의 return
        } catch (e: Exception) {
            connection?.discard()
            throw e
        }
    }



}