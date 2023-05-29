package estert.service

import estert.domain.deal.Deal
import estert.domain.deal.DealRepository
import estert.domain.deal.DealService
import estert.domain.deal.dto.DealSaveRequest
import estert.domain.house.House
import estert.domain.house_detail.HouseDetail
import estert.domain.house_detail.HouseDetailRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import java.util.*

class DealServiceTest : BehaviorSpec({
    val dealRepository = mockk<DealRepository>()
    val houseDetailRepository = mockk<HouseDetailRepository>()
    val dealService = DealService(dealRepository, houseDetailRepository)

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

    Given("deal 저장 요청시") {
        val request = DealSaveRequest(
            cost = 100000000,
            dealDate = LocalDateTime.now().toString(),
            houseDetailId = 1L
        )
        When("정상 요청을 하면") {
            every { houseDetailRepository.findById(any()).get() } returns houseDetail
            every { dealRepository.save(any()).id } returns 1L
            val response = dealService.save(request)
            Then("deal 이 저장된다") {
                verify { dealRepository.save(any()) }
            }
            Then("deal 아이디가 반환된다") {
                response shouldBe 1L
            }
            Then("deal 은 houseDetail 과 N:1 관계가 형성된다") {
                deal.houseDetail shouldBe houseDetail
            }
            Then("houseDetail 은 deals 와 1:N 관계가 형성된다") {
                houseDetail.deals shouldContain deal
            }
        }
        When("해당 Id 의 houseDetail 이 존재하지 않으면") {
            every { houseDetailRepository.findById(any()) } returns Optional.empty()
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    dealService.save(request)
                }
            }
        }
    }
})