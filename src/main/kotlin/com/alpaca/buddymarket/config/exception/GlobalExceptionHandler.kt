package com.alpaca.buddymarket.config.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(MyException::class)
    fun handleCustomException(ex: MyException): ErrorResponse {
        val problemDetail =
            makeProblemDetail(ex)

        return ErrorResponse
            .builder(ex, problemDetail)
            .build()
    }

    private fun makeProblemDetail(ex: MyException): ProblemDetail {
        val problemDetail =
            ProblemDetail
                .forStatusAndDetail(
                    ex.errorCode.httpStatus,
                    ex.message ?: "Unexpected error occurred.",
                ).apply {
                    setProperty("code", ex.errorCode.name)
                    setProperty("timestamp", LocalDateTime.now())
                }
        return problemDetail
    }

    // ValidationException 처리
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ErrorResponse {
        val errorMessage =
            ex.bindingResult.fieldErrors
                .joinToString(", ") { "${it.field}: ${it.defaultMessage}" }

        return ErrorResponse
            .builder(ex, HttpStatusCode.valueOf(400), errorMessage)
            .build()
    }

    // 나머지 모든 예외 처리
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception): ErrorResponse {
        logger.error("Unexpected error occurred:", ex)

        return ErrorResponse
            .builder(ex, HttpStatusCode.valueOf(500), "Unexpected error occurred.")
            .build()
    }
}
