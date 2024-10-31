package com.alpaca.buddymarket.config.exception

class MyException(
    val errorCode: ErrorCode,
    message: String? = null,
) : RuntimeException(message ?: errorCode.message)
