package com.canevi.stock.manage.config.aspect

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import io.github.resilience4j.ratelimiter.annotation.RateLimiter
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class GlobalResilienceAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    fun allRestControllers() {
    }

    @CircuitBreaker(name = "default")
    @Retry(name = "default")
    @RateLimiter(name = "default")
    @Around("allRestControllers()")
    fun applyResilience(joinPoint: ProceedingJoinPoint): Any? {
        return try {
            joinPoint.proceed()
        } catch (e: Throwable) {
            throw e
        }
    }
}