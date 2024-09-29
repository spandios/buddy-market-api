package com.alpaca.buddymarket.config.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val message: String = "",
    val httpStatus: HttpStatus,
    val code: String? = "",
) {
    // AUTH
    USER_NOT_FOUND("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    UNEXPECTED_ERROR("INTERNAL SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_ALREADY_EXISTS("이미 가입된 유저입니다.", HttpStatus.BAD_REQUEST),

    // POST
    POST_NOT_FOUND("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_TOKEN("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),
}
