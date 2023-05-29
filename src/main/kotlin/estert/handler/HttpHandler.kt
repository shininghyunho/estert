package estert.handler

import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

// logger
private val log = LoggerFactory.getLogger(HttpHandler::class.java)
class HttpHandler {
    companion object {
        fun get(url: URL):String {
            val connection= try {
                // connection
                url.openConnection() as HttpURLConnection
            } catch (e: Exception) {
                log.error("url connection 연결 실패 : ${e.message}")
                throw e
            }

            // set method
            connection.requestMethod = "GET"
            // set JSON
            connection.setRequestProperty("Content-Type", "application/json")

            // response
            val response = StringBuilder()
            try {
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val br = BufferedReader(connection.inputStream.reader())
                    while(true) {
                        val inputLine = br.readLine() ?: break
                        response.append(inputLine)
                    }
                    br.close()
                } else {
                    val msg="GET 상태코드가 200이 아닙니다. 상태코드: ${connection.responseCode}"
                    log.error(msg)
                    throw Exception(msg)
                }
            } catch (e: Exception) {
                log.error("GET 요청 실패 : ${e.message}")
                throw e
            } finally {
                connection.disconnect()
            }

            return response.toString()
        }
    }
}