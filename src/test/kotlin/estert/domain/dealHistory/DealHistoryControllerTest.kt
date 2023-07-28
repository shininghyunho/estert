package estert.domain.dealHistory

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import estert.common.CustomResponseEntity
import estert.domain.dealHistory.dto.filter.DealHistoryFilterRequest
import estert.domain.dealHistory.dto.filter.DealHistoryFilterResponse
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@WebMvcTest
class DealHistoryControllerTest: BehaviorSpec({
    val dealHistoryService = mockk<DealHistoryService>()
    val dealHistoryController = DealHistoryController(dealHistoryService)
    val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(dealHistoryController).build()

    /**
     * 기존 objectMapper 는 기본 생성자를 사용해서 각각 필드에 값을 넣어줘야 했지만
     *  jacksonObjectMapper 는 기본 생성자를 사용하지 않고, 필드에 값을 넣어줄 필요가 없다.
     */
    val objectMapper = jacksonObjectMapper()


    Given("GET /deal-history 요청시") {
        val url = "/api/v1/deal-history/filter"
        val request = DealHistoryFilterRequest(
            latitude = 36.5,
            longitude = 128.5,
            lowCost = 4000,
            highCost = 5000,
            time = 30,
            count = 10,
        )
        val filteredDealHistoryList = listOf(
            DealHistoryFilterResponse.FilteredDealHistory(
                dealId = 1,
                latitude = 36.5,
                longitude = 127.5,
                estimatedTime = 30),
            DealHistoryFilterResponse.FilteredDealHistory(
                dealId = 2,
                latitude = 35.5,
                longitude = 128.5,
                estimatedTime = 20),
            DealHistoryFilterResponse.FilteredDealHistory(
                dealId = 3,
                latitude = 36.5,
                longitude = 126.5,
                estimatedTime = 10),
        )
        every { dealHistoryService.filter(any()) } returns DealHistoryFilterResponse(filteredDealHistoryList)
        When("정상 요청을 하면") {
            val response = mockMvc.perform(
                get(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()

            val responseStr = response.response.contentAsString
            val map = objectMapper.readValue(responseStr, Map::class.java)
            val data = objectMapper.convertValue(map["data"], DealHistoryFilterResponse::class.java)

            Then("필터링된 데이터가 반환된다.") {
                data.dealHistoryList.forEach{
                    print("dealId: ${it.dealId}, latitude: ${it.latitude}, longitude: ${it.longitude}, estimatedTime: ${it.estimatedTime}\n")
                    it.dealId shouldBeIn filteredDealHistoryList.map { filteredDealHistory -> filteredDealHistory.dealId }
                }
            }
        }
    }
})