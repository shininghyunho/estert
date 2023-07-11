package estert.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

class CustomResponseEntity(
    private val status: HttpStatus,
    private val data: Any? = null,
    private val message: String? = null,
    private val error: String? = null,
){
    // custom body
    class Body(
        val message: String,
        val data: Any? = null,
        val timeStamp: LocalDateTime = LocalDateTime.now(),
        val error: String? = null,
    )

    fun toResponseEntity(): ResponseEntity<Body> {
        return ResponseEntity
            .status(status)
            .body(Body(
                message = message ?: status.reasonPhrase,
                data = data,
                error = error,
            ))
    }
}