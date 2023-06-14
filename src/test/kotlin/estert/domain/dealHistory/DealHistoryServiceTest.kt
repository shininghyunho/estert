package estert.domain.dealHistory

import estert.common.handler.AddressHandler
import estert.domain.deal.Deal
import estert.domain.deal.DealService
import estert.domain.house.House
import estert.domain.house.HouseService
import estert.domain.house.dto.HouseGetResponse
import estert.domain.house_detail.HouseDetail
import estert.domain.house_detail.HouseDetailService
import estert.domain.molitApart.MolitApartService
import estert.dummy.DummyEntity
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime

class DealHistoryServiceTest: BehaviorSpec({
    val houseService = mockk<HouseService>()
    val houseDetailService = mockk<HouseDetailService>()
    val dealService = mockk<DealService>()
    val molitApartService = mockk<MolitApartService>()
    val addressHandler = mockk<AddressHandler>()
    val dealHistoryService = DealHistoryService(houseService, houseDetailService, dealService, molitApartService, addressHandler)

    val house = DummyEntity.house
    val houseDetail = DummyEntity.houseDetail
    val deal = DummyEntity.deal

    Given("거래 내역 조회시") {
        When("부동산 ID로 조회 요청하면") {
            every { houseService.findByIdWithHouseDetails(any()) } returns HouseGetResponse.from(house)
            val response = dealHistoryService.get(1L)
            Then("부동산 거래 내역이 반환된다") {}
        }
        When("부동산 ID가 존재하지 않으면") {
            Then("Exception 이 발생한다")
        }
    }

    // disable
    xGiven("거래 내역 저장시") {
        When("정상 요청을 하면") {
            every { molitApartService.getMolitApartList(any()) } returns listOf()
        }
        When("부동산 ID가 존재하지 않으면") {
            Then("Exception 이 발생한다")
        }
    }
})