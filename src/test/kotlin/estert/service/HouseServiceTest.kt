package estert.service

import estert.domain.house.House
import estert.domain.house.HouseRepository
import estert.domain.house.HouseService
import estert.domain.house.dto.HouseSaveRequest
import estert.domain.house.dto.HouseUpdateRequest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal
import java.util.*

val houseRepository = mockk<HouseRepository>()
val houseService = HouseService(houseRepository)
val house = House(
    jibunAddress = "jibunAddress",
    roadAddress = "roadAddress",
    danjiName = "danjiName",
    postCode = 12345,
    latitude = BigDecimal("123.123"),
    longitude = BigDecimal("123.123"))
class HouseServiceBehaviorSpecTest: BehaviorSpec({
    // house 저장 테스트
    Given("부동산 저장 요청시") {
        val request = HouseSaveRequest.of(house)
        When("정상 요청을 하면") {
            every { houseRepository.save(any()).id } returns 1L
            val response=houseService.save(request)
            Then("부동산이 저장된다") { verify { houseRepository.save(any()) } }
            Then("부동산 아이디가 반환된다") { response shouldBe 1L }
        }
        When("파라미터가 옳바르지 못하다면") {
            every { houseRepository.save(any()) } throws Exception()
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    houseService.save(request)
                }
            }
        }
    }

    // house 조회 테스트
    Given("부동산 조회시") {
        When("존재하는 ID로 조회 요청하면") {
            every { houseRepository.findById(any()) } returns Optional.of(house)
            val response = houseService.findById(1L)
            Then("부동산이 반환된다") {
                response.jibunAddress shouldBe "jibunAddress"
            }
        }
        When("존재하지 않는 ID로 조회 요청하면") {
            every { houseRepository.findById(any()) } returns Optional.empty()
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    houseService.findById(1L)
                }
            }
        }
        When("유일한 (도로명,단지명)으로 조회하면") {
            every { houseRepository.findByRoadAddressAndDanjiName(any(), any()) } returns house
            val response = houseService.findByRoadAddressAndDanjiName("roadAddress", "danjiName")
            Then("부동산이 반환된다") {
                response.jibunAddress shouldBe "jibunAddress"
            }
        }
        When("유일하지 않은 (도로명,단지명) 으로 조회하면") {
            every { houseRepository.findByRoadAddressAndDanjiName(any(), any()) } throws Exception()
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    houseService.findByRoadAddressAndDanjiName("roadAddress", "danjiName")
                }
            }
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    houseService.findByRoadAddressAndDanjiName("roadAddress", "danjiName")
                }
            }
        }
        When("존재하지 않는 (도로명,단지명) 으로 조회하면") {
            every { houseRepository.findByRoadAddressAndDanjiName(any(), any()) } throws Exception()
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    houseService.findByRoadAddressAndDanjiName("roadAddress", "danjiName")
                }
            }
        }
    }
    // 부동산 수정 테스트
    Given("부동산 수정시"){
        When("존재하는 ID로 수정시") {
            every { houseRepository.findById(any()) } returns Optional.of(house)
            houseService.update(1L, HouseUpdateRequest(jibunAddress = "modified_jibunAddress"))
            Then("부동산이 수정된다") {
                verify { houseRepository.save(any()) }
            }
        }
        When("존재하지 않는 ID로 수정시") {
            every { houseRepository.findById(any()) } returns Optional.empty()
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    houseService.update(1L, HouseUpdateRequest(jibunAddress = "modified_jibunAddress"))
                }
            }
        }
        When("중복된 (도로명,단지명) 으로 수정시") {
            every { houseRepository.findByRoadAddressAndDanjiName(any(), any()) } returns house
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    houseService.update(1L, HouseUpdateRequest(jibunAddress = "modified_jibunAddress"))
                }
            }
        }
    }
    // 부동산 삭제 테스트
    Given("부동산 삭제시") {
        When("존재하는 ID로 삭제시") {
            every { houseRepository.deleteById(any()) } returns Unit
            houseService.deleteById(1L)
            Then("부동산이 삭제된다") { verify { houseRepository.deleteById(any()) } }
        }
        When("존재하지 않는 ID로 삭제시") {
            every { houseRepository.deleteById(any()) } throws Exception()
            Then("Exception 이 발생한다") {
                shouldThrow<Exception> {
                    houseService.deleteById(1L)
                }
            }
        }
    }
})