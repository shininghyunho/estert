package estert.domain.api.predict

import com.google.gson.Gson
import estert.common.handler.HttpHandler
import estert.domain.api.predict.dto.PredictRequest
import estert.domain.api.predict.dto.PredictResponse
import estert.dummy.DummyEntity
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlinx.coroutines.test.TestResult

class PredictHandlerTest : BehaviorSpec() {
    init {
        mockkObject(HttpHandler) // object 클래스 mocking

        // unmockObject
        afterSpec {
            unmockkObject(HttpHandler)
        }

        val gson = mockk<Gson>()

        val predictHandler = PredictHandler(gson)

        Given("예측 요청시") {
            val request = PredictRequest(
                time = 1,
                latitude = 36.5,
                longitude = 127.5,
            )
            When("정상 요청을 하면") {
                every { HttpHandler.get(any()) } returns ""
                every { gson.fromJson("", any<Class<Array<PredictResponse.PredictHouse>>>()) } returns DummyEntity.predictHouseList.toTypedArray()
                val response = predictHandler.predict(request)
                Then("예측 결과를 반환한다") {
                    response.predictHouseList.size shouldBe DummyEntity.predictHouseList.size
                    // print all
                    response.predictHouseList.forEach {
                        println("id: ${it.id}, time: ${it.time}")
                    }
                }
            }
        }
    }
}