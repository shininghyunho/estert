package estert.common.error

import org.springframework.http.HttpStatus

class CustomException (
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
    override val message: String = status.reasonPhrase,
    val detail : String? = null,
) : RuntimeException()