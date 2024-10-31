package com.alpaca.buddymarket.config.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    init {
        logger.info("com.alpaca.buddymarket.config.exception.GlobalExceptionHandler initialized")
    }

    data class ErrorResponse(
        val code: String,
        val message: String,
        val timestamp: LocalDateTime = LocalDateTime.now(),
    )

    @ExceptionHandler(MyException::class)
    fun handleCustomException(ex: MyException): ResponseEntity<ProblemDetail> {
        val problemDetail =
            ProblemDetail
                .forStatusAndDetail(
                    ex.errorCode.httpStatus,
                    ex.message ?: "An error occurred",
                ).apply {
                    setProperty("code", ex.errorCode.name)
                    setProperty("timestamp", LocalDateTime.now())
                }

        return ResponseEntity
            .status(ex.errorCode.httpStatus)
            .body(problemDetail)
    }

    // ValidationException 처리
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorMessage =
            ex.bindingResult.fieldErrors
                .joinToString(", ") { "${it.field}: ${it.defaultMessage}" }

        val response =
            ErrorResponse(
                code = "VALIDATION_ERROR",
                message = errorMessage,
            )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(response)
    }

    // HTTP 메서드 불일치 처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotSupported(ex: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponse> {
        val response =
            ErrorResponse(
                code = "METHOD_NOT_ALLOWED",
                message = "${ex.method} 메서드는 지원하지 않습니다.",
            )

        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(response)
    }

    // 권한 없음 처리
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(ex: AccessDeniedException): ResponseEntity<ErrorResponse> {
        val response =
            ErrorResponse(
                code = "ACCESS_DENIED",
                message = "접근 권한이 없습니다.",
            )

        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(response)
    }

    // 리소스 없음 처리
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElement(ex: NoSuchElementException): ResponseEntity<ErrorResponse> {
        val response =
            ErrorResponse(
                code = "NOT_FOUND",
                message = "요청한 리소스를 찾을 수 없습니다.",
            )

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(response)
    }

    // 나머지 모든 예외 처리
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Unexpected error occurred:", ex)

        val response =
            ErrorResponse(
                code = "INTERNAL_SERVER_ERROR",
                message = "내부 서버 오류가 발생했습니다.",
            )

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(response)
    }
}
