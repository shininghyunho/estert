package estert.service

import estert.domain.house.House
import estert.domain.house.HouseRepository
import estert.domain.house.HouseService
import estert.domain.house.dto.HouseSaveRequest
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockKExtension::class)
class HouseServiceTest {
    private val houseRepository = mockk<HouseRepository>()
    private val houseService = HouseService(houseRepository)

    @DisplayName("[성공]부동산 저장")
    @Test
    fun saveTest() {
        // given
        val request = HouseSaveRequest(
            jibunAddress = "jibunAddress",
            roadAddress = "roadAddress",
            danjiName = "danjiName",
            postCode = 12345,
            latitude = "123.123",
            longitude = "123.123")
        every { houseRepository.save(any()).id } returns 1L
        // when
        val response=houseService.save(request)
        // then
        // response 가 1L 인지 확인 (with kotest)
        response shouldBe 1L
        // verify : mockk 객체의 메소드 호출 여부를 검증
        verify { houseRepository.save(any()) }
    }

    @DisplayName("[성공]부동산 ID로 조회")
    @Test
    fun findByIdTest() {
        // given
        val house = House(
            jibunAddress = "jibunAddress",
            roadAddress = "roadAddress",
            danjiName = "danjiName",
            postCode = 12345,
            latitude = BigDecimal("123.123"),
            longitude = BigDecimal("123.123"))

        every { houseRepository.findById(any()) } returns Optional.of(house)
        // when
        houseService.findById(1L)
        // then
        verify { houseRepository.findById(any()) }
    }
}