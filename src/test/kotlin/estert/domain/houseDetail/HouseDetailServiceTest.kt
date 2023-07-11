package estert.domain.houseDetail

import estert.domain.house.House
import estert.domain.house.HouseRepository
import estert.domain.house_detail.HouseDetail
import estert.domain.house_detail.HouseDetailRepository
import estert.domain.house_detail.HouseDetailService
import estert.domain.house_detail.dto.HouseDetailSaveRequest
import estert.dummy.DummyEntity
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class HouseDetailServiceTest : BehaviorSpec({
    val houseDetailRepository = mockk<HouseDetailRepository>()
    val houseRepository = mockk<HouseRepository>()
    val houseDetailService = HouseDetailService(houseDetailRepository, houseRepository)

    val house = DummyEntity.house
    val houseDetail = DummyEntity.houseDetail

    // houseDetail 저장 테스트
    Given("houseDetail 저장 요청시") {
        val request = HouseDetailSaveRequest(
            dedicatedArea = "123.123",
            houseId = 1L
        )
        When("정상 요청을 하면") {
            every { houseRepository.findById(any()).get() } returns house
            every { houseDetailRepository.save(any()).id } returns 1L
            val response = houseDetailService.save(request)
            Then("houseDetail 이 저장된다") {
                verify { houseDetailRepository.save(any()) }
            }
            Then("houseDetail 아이디가 반환된다") {
                response shouldBe 1L
            }
            Then("house 와 1:N 관계가 형성된다") {
                houseDetail.house shouldBe house
            }
        }
        When("house 가 존재하지 않으면") {
            every { houseRepository.findById(any()) } returns Optional.empty()
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    houseDetailService.save(request)
                }
            }
        }
    }
})