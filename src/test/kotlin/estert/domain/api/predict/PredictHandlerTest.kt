package estert.domain.api.predict

import estert.domain.api.predict.dto.PredictRequest
import estert.dummy.DummyEntity
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class PredictHandlerTest : BehaviorSpec({
    val predictHandler = PredictHandler()

    val house = DummyEntity.house

    Given("예측 요청시") {
        val request = PredictRequest(
            time = 1,
            price = 1,
            latitude = "1",
            longitude = "1",
        )
        When("정상 요청을 하면") {
            val response = predictHandler.getPredictList(request)
            Then("예측 결과를 반환한다") {
                response shouldBe listOf()
            }
        }
    }
})