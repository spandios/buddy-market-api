import com.alpaca.buddymarket.config.exception.ErrorCode

class MyException(
    errorCode: ErrorCode,
    message: String? = null,
) : RuntimeException(message ?: errorCode.message)
