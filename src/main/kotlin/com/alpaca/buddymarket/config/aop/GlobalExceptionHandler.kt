import com.alpaca.buddymarket.config.exception.MyException
import org.springframework.http.ProblemDetail
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MyException::class)
    fun handleCustomException(ex: MyException): ErrorResponse {
        val error: ErrorResponse =
            ErrorResponse
                .builder(ex, ProblemDetail.forStatusAndDetail(ex.errorCode.httpStatus, ex.message))
                .build()
        return error
    }
}
