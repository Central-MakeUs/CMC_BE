package com.example.cmc_be.common.exeption

import com.example.cmc_be.common.response.CommonResponse
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors
import java.util.stream.StreamSupport
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.Path

@Slf4j
@RestControllerAdvice
class ExceptionAdvice {
    val log = KotlinLogging.logger {}



    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onConstraintValidationException(e: ConstraintViolationException): ResponseEntity<*>? {
        val errors = e.constraintViolations.stream()
            .collect(
                Collectors.toMap(
                    { violation: ConstraintViolation<*> ->
                        StreamSupport.stream(violation.propertyPath.spliterator(), false)
                            .reduce { first: Path.Node?, second: Path.Node -> second }
                            .get().toString()
                    },
                    { obj: ConstraintViolation<*> -> obj.message })
            )
        return ResponseEntity<Any?>(
            CommonResponse.onFailure("REQUEST_ERROR", "요청 형식 에러 result 확인해주세요", errors),
            null,
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<*>? {
        val errors: MutableMap<String, String> = LinkedHashMap()
        for (fieldError in e.bindingResult.fieldErrors) {
            val fieldName = fieldError.field
            var errorMessage = Optional.ofNullable(fieldError.defaultMessage).orElse("")
            if (errors.containsKey(fieldName)) {
                val existingErrorMessage = errors[fieldName]
                errorMessage = "$existingErrorMessage, $errorMessage"
            }
            errors[fieldName] = errorMessage
        }
        return ResponseEntity<Any?>(
            CommonResponse.onFailure("REQUEST_ERROR", "요청 형식 에러 result 확인해주세요", errors),
            null,
            HttpStatus.BAD_REQUEST
        )
    }


    private fun getExceptionStackTrace(
        e: Exception, @AuthenticationPrincipal user: User?,
        request: HttpServletRequest
    ) {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        pw.append("\n==========================!!!ERROR TRACE!!!==========================\n")
        pw.append("uri: " + request.requestURI + " " + request.method + "\n")
        if (user != null) {
            pw.append("uid: " + user.username + "\n")
        }
        pw.append(e.message)
        pw.append("\n=====================================================================")
        log.error(sw.toString())
    }


    @ExceptionHandler(value = [BaseException::class])
    fun onKnownException(
        baseException: BaseException,
        @AuthenticationPrincipal user: User?, request: HttpServletRequest
    ): CommonResponse<*> {
        getExceptionStackTrace(baseException, user, request)
        log.info(baseException.errorReasonHttpStatus!!.getCode())
        log.info(baseException.message)
        return CommonResponse.onFailure(
            baseException.errorReasonHttpStatus?.getCode(),
            baseException.errorReasonHttpStatus?.getMessage(),
            baseException.errorReasonHttpStatus?.getResult()
        )
    }


}