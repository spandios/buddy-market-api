package com.alpaca.buddymarket.config.exception

class BException(
    val errorCode: ErrorCode,
    message: String? = null,
) : RuntimeException(message ?: errorCode.message)
