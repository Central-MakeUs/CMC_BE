package com.example.cmc_be.common.annotation

import org.springframework.stereotype.Component


@Target(AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class ExplainError(val value: String = "")

