package estert

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EstertApplication

fun main(args: Array<String>) {
    runApplication<EstertApplication>(*args)
}
