package estert.handler

import estert.common.handler.HttpHandler
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.string.shouldContain
import io.mockk.every
import io.mockk.mockk
import java.io.ByteArrayInputStream
import java.net.HttpURLConnection
import java.net.URL

class HttpHandlerTest: BehaviorSpec({
    Given("GET 요청시") {
        When("정상 요청을 하면") {
            // string to input stream
            val mockResponse = "mock response"
            val url = mockk<URL>() {
                every { openConnection() } returns mockk<HttpURLConnection>(relaxed = true) {
                    every { responseCode } returns HttpURLConnection.HTTP_OK
                    every { inputStream } returns ByteArrayInputStream(mockResponse.toByteArray())
                }
            }
            val response = HttpHandler.get(url)
            Then("response body 가 반환된다") {
                response shouldContain mockResponse
            }
        }

        When("URL 이 옳바르지 못한다면") {
            val url = mockk<URL>() {
                every { openConnection() } throws Exception()
            }
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    HttpHandler.get(url)
                }
            }
        }

        When("응답 코드가 200이 아니면") {
            val url = mockk<URL>() {
                every { openConnection() } returns mockk<HttpURLConnection>(relaxed = true) {
                    every { responseCode } returns HttpURLConnection.HTTP_BAD_REQUEST
                }
            }
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    HttpHandler.get(url)
                }
            }
        }
    }
})