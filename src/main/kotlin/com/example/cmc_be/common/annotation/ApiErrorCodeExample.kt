package com.backend.cmcapi.common.annotation

import com.example.cmc_be.common.exeption.errorcode.BaseErrorCode
import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiErrorCodeExample(
    vararg val value: KClass<out BaseErrorCode>
)
