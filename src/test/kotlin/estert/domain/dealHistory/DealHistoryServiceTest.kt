package estert.domain.dealHistory

import estert.common.handler.AddressHandler
import estert.domain.deal.DealService
import estert.domain.dealHistory.dto.DealHistorySaveRequest
import estert.domain.house.HouseService
import estert.domain.house.dto.HouseGetResponse
import estert.domain.house_detail.HouseDetailService
import estert.domain.api.molitApart.MolitApartHandler
import estert.domain.api.predict.PredictHandler
import estert.dummy.DummyEntity
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class DealHistoryServiceTest: BehaviorSpec({
    val houseService = mockk<HouseService>()
    val houseDetailService = mockk<HouseDetailService>()
    val dealService = mockk<DealService>()
    val molitApartHandler = mockk<MolitApartHandler>()
    val addressHandler = mockk<AddressHandler>()
    val predictHandler = mockk<PredictHandler>()
    val dealHistoryService = DealHistoryService(
        houseService,
        houseDetailService,
        dealService,
        molitApartHandler,
        addressHandler,
        predictHandler
    )

    val house = DummyEntity.house
    val houseDetail = DummyEntity.houseDetail
    val deal = DummyEntity.deal
    val molitApart = DummyEntity.molitApart

    Given("거래 내역 조회시") {
        When("부동산 ID로 조회 요청하면") {
            every { houseService.findByIdWithHouseDetails(any()) } returns HouseGetResponse.from(house)
            val response = dealHistoryService.get(1L)
            Then("부동산 거래 내역이 반환된다") {
                response.house shouldNotBe null
                response.house.id shouldBe house.id
            }
        }
        When("부동산 ID가 존재하지 않으면") {
            every { houseService.findByIdWithHouseDetails(any()) } throws Exception()
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    dealHistoryService.get(1L)
                }
            }
        }
        When("존재하는 도로명,단지명 으로 조회 요청하면") {
            every { houseService.findByRoadAddressAndDanjiNameWithHouseDetails(any(), any()) } returns HouseGetResponse.from(house)
            val response = dealHistoryService.get("roadAddress", "danjiName")
            Then("부동산 거래 내역이 반환된다") {
                response.house shouldNotBe null
                response.house.id shouldBe house.id
            }
        }
        When("존재하지 않는 도로명,단지명 으로 조회 요청하면") {
            every { houseService.findByRoadAddressAndDanjiNameWithHouseDetails(any(), any()) } throws Exception()
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    dealHistoryService.get("roadAddress", "danjiName")
                }
            }
        }
    }

    Given("거래 내역 저장시") {
        When("정상 요청을 하면") {
            every { molitApartHandler.getMolitApartList(any()) } returns listOf(molitApart, molitApart)
            every { addressHandler.makeHouseSaveRequest(any()) } returns mockk()
            every { houseService.saveAndReturnEntity(any()) } returns house
            every { houseDetailService.saveAndReturnEntity(any()) } returns houseDetail
            every { dealService.save(any()) } returns 1L
            every { molitApartHandler.getTotalCount() } returns 10
            val response = dealHistoryService.save(DealHistorySaveRequest(pageNo = 1, perPage = 10, lawdCd = "11110", dealYmd = "202101"))
            Then("거래 내역이 저장된다") {
                verify { houseService.saveAndReturnEntity(any()) }
                verify { houseDetailService.saveAndReturnEntity(any()) }
                verify { dealService.save(any()) }
            }
            Then("전체 거래 내역 갯수가 반환된다") {
                response shouldBeGreaterThan 0
            }
        }
        When("Molit Apart Api 호출에 실패하면") {
            every { molitApartHandler.getMolitApartList(any()) } throws Exception()
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    dealHistoryService.save(DealHistorySaveRequest(pageNo = 1, perPage = 10, lawdCd = "11110", dealYmd = "202101"))
                }
            }
        }
    }

    Given("거래 내역 필터링시") {
        When("정상 요청하면") {
            Then("거래내역이 필터링 된다.") {
            }
        }
    }
})