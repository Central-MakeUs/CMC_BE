package com.example.cmc_be.common.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Component
@Aspect
class LogAspect{
    @Pointcut("execution(* com.example.cmc_be..*Controller.*(..))")
    fun controller() {
    }


    @Pointcut("execution(* com.example.cmc_be..*Service.*(..))")
    fun service() {
    }


    @Before("controller()")
    @Throws(Throwable::class)
    fun beforeLogic(joinPoint: JoinPoint) {
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        val methodName = getMethodName(method)
        log.info("==========================LOG_START==========================")
        log.info("logging start method = {}", methodName)
        val parameterNames = methodSignature.parameterNames
        val args = joinPoint.args
        var index = 0
        for (arg in args) {
            if (arg != null) {
                log.info(
                    "parameterNames = {} type = {}, value = {}",
                    parameterNames[index], arg.javaClass.simpleName, arg.toString()
                )
            }
            index += 1
        }
    }

    private fun getMethodName(method: Method): String {
        return method.name
    }

    @After("controller()")
    @Throws(Throwable::class)
    fun afterLogic(joinPoint: JoinPoint) {
        val method = getMethod(joinPoint)
        val methodName = getMethodName(method)
        log.info("logging finish method = {}", methodName)
        log.info("==========================LOG_FINISH==========================")
        }

    @AfterReturning(value = "controller()", returning = "returnObj")
    fun afterReturnLog(joinPoint: JoinPoint, returnObj: Any?) {
        val method = getMethod(joinPoint)
        val methodName = getMethodName(method)
        log.info("========================RETURN_LOG============================")
        log.info("method name = {}", methodName)
        if (returnObj != null) {
            log.info("return type = {}", returnObj.javaClass.simpleName)
        }
        log.info("return value = {}", returnObj.toString())
        log.info("==============================================================")
    }


    private fun getMethod(joinPoint: JoinPoint): Method {
        val signature = joinPoint.signature as MethodSignature
        return signature.method
    }

    companion object {
        private val log = LoggerFactory.getLogger(LogAspect::class.java)
    }



}