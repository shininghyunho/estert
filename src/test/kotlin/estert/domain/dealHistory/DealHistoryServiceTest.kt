package estert.domain.dealHistory

import estert.domain.deal.Deal
import estert.domain.house.House
import estert.domain.house.HouseService
import estert.domain.house.dto.HouseGetResponse
import estert.domain.house_detail.HouseDetail
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime

class DealHistoryServiceTest: BehaviorSpec({
    val houseService = mockk<HouseService>()
    val dealHistoryService = DealHistoryService(houseService)

    val house = House(
        jibunAddress = "jibunAddress",
        roadAddress = "roadAddress",
        danjiName = "danjiName",
        postCode = 12345,
        latitude = "123.123".toBigDecimal(),
        longitude = "123.123".toBigDecimal()
    )
    val houseDetail = HouseDetail(
        dedicatedArea = "123.123".toBigDecimal(),
        house = house
    )
    val deal = Deal(
        cost = 100000000,
        dealDate = LocalDateTime.now(),
        houseDetail = houseDetail
    )

    Given("거래 내역 조회시") {
        When("부동산 ID로 조회 요청하면") {
            every { houseService.findByIdWithHouseDetails(any()) } returns HouseGetResponse.from(house)
            val response = dealHistoryService.getDealHistory(1L)
            Then("부동산 거래 내역이 반환된다") {}
        }
        When("부동산 ID가 존재하지 않으면") {
            Then("Exception 이 발생한다")
        }
    }
})