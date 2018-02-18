package com.github.tophatcroat.kotlinspringbootstrap.web.auth

import com.github.tophatcroat.kotlinspringbootstrap.exception.AuthException
import com.github.tophatcroat.kotlinspringbootstrap.domain.model.User
import com.github.tophatcroat.kotlinspringbootstrap.service.UserService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.lang.annotation.Inherited


@MustBeDocumented
@Inherited
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireAuth(val mandatory: Boolean = true)

@Aspect
@Component
class RequireAuthAspect(@Autowired val userService: UserService) {

    @Pointcut(value = "execution(@com.github.tophatcroat.kotlinspringbootstrap.web.auth.RequireAuth * *.*(..))")
    fun authenticatedEndpoint() {
    }

    @Around("authenticatedEndpoint()")
    @Throws(Throwable::class)
    fun aroundAuthenticatedEndpoint(joinPoint: ProceedingJoinPoint): Any {
        val servletContainer = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val request = servletContainer.request

        if (request.method == "OPTIONS")
            return joinPoint.proceed()

        val token = request.getHeader("Authorization")

        if (token.isNullOrBlank()) {
            throw AuthException()
        }

        val user: User? = userService.findByToken(token as String) ?: throw AuthException()

        userService.setCurrentUser(user!!)
        val result = joinPoint.proceed()
        userService.setCurrentUser(null)
        return result
    }

}