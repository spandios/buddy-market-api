
import com.alpaca.buddymarket.config.exception.BException
import org.springframework.http.ProblemDetail
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BException::class)
    fun handleCustomException(ex: BException): ErrorResponse {
        val error: ErrorResponse =
            ErrorResponse
                .builder(ex, ProblemDetail.forStatusAndDetail(ex.errorCode.httpStatus, ex.message))
                .build()
        return error
    }
}
