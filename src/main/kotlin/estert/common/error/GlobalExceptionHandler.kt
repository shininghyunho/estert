package estert.common.error

import estert.common.CustomResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    // custom exception
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<CustomResponseEntity.Body> {
        return CustomResponseEntity(
            status = e.status,
            message = e.message,
            error = e.detail
        ).toResponseEntity()
    }

    // server exception
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<CustomResponseEntity.Body> {
        return CustomResponseEntity(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            message = "서버 에러가 발생했습니다.",
            error = e.message
        ).toResponseEntity()
    }
}