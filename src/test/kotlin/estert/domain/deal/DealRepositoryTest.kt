package estert.domain.deal

import estert.domain.house.House
import estert.domain.house.HouseRepository
import estert.domain.house_detail.HouseDetail
import estert.domain.house_detail.HouseDetailRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime
import java.util.stream.Stream

@DataJpaTest
class DealRepositoryTest(
    @Autowired val houseRepository: HouseRepository,
    @Autowired val houseDetailRepository: HouseDetailRepository,
    @Autowired val dealRepository: DealRepository,
): BehaviorSpec({
    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

    fun saveFilterEntity() {
        // house 3개 저장
        val house1 = houseRepository.save(
            House(
                jibunAddress = "jibunAddress1",
                roadAddress = "roadAddress1",
                danjiName = "danjiName1",
                postCode = "12345",
                latitude = "36.5".toBigDecimal(),
                longitude = "128.5".toBigDecimal()
            )
        )
        val house2 = houseRepository.save(
            House(
                jibunAddress = "jibunAddress2",
                roadAddress = "roadAddress2",
                danjiName = "danjiName2",
                postCode = "12345",
                latitude = "36.4".toBigDecimal(),
                longitude = "128.2".toBigDecimal()
            )
        )
        val house3 = houseRepository.save(
            House(
                jibunAddress = "jibunAddress3",
                roadAddress = "roadAddress3",
                danjiName = "danjiName3",
                postCode = "12345",
                latitude = "36.3".toBigDecimal(),
                longitude = "128.1".toBigDecimal()
            )
        )

        // 각 house 당 houseDetail 3개씩 저장
        val houseDetail1 = houseDetailRepository.save(
            HouseDetail(
                dedicatedArea = "123.123".toBigDecimal(),
                house = house1
            )
        )
        val houseDetail2 = houseDetailRepository.save(
            HouseDetail(
                dedicatedArea = "124.123".toBigDecimal(),
                house = house1
            )
        )
        val houseDetail3 = houseDetailRepository.save(
            HouseDetail(
                dedicatedArea = "125.123".toBigDecimal(),
                house = house1
            )
        )
        val houseDetail4 = houseDetailRepository.save(
            HouseDetail(
                dedicatedArea = "126.123".toBigDecimal(),
                house = house2
            )
        )
        val houseDetail5 = houseDetailRepository.save(
            HouseDetail(
                dedicatedArea = "127.123".toBigDecimal(),
                house = house2
            )
        )
        val houseDetail6 = houseDetailRepository.save(
            HouseDetail(
                dedicatedArea = "128.123".toBigDecimal(),
                house = house2
            )
        )
        val houseDetail7 = houseDetailRepository.save(
            HouseDetail(
                dedicatedArea = "129.123".toBigDecimal(),
                house = house3
            )
        )
        val houseDetail8 = houseDetailRepository.save(
            HouseDetail(
                dedicatedArea = "130.123".toBigDecimal(),
                house = house3
            )
        )
        val houseDetail9 = houseDetailRepository.save(
            HouseDetail(
                dedicatedArea = "131.123".toBigDecimal(),
                house = house3
            )
        )

        // 각 houseDetail 당 deal 3개씩 저장
        dealRepository.save(
            Deal(
                cost = 1000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail1
            )
        )
        dealRepository.save(
            Deal(
                cost = 2000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail1
            )
        )
        dealRepository.save(
            Deal(
                cost = 3000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail1
            )
        )
        dealRepository.save(
            Deal(
                cost = 4000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail2
            )
        )
        dealRepository.save(
            Deal(
                cost = 5000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail2
            )
        )
        dealRepository.save(
            Deal(
                cost = 6000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail2
            )
        )
        dealRepository.save(
            Deal(
                cost = 7000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail3
            )
        )
        dealRepository.save(
            Deal(
                cost = 1000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail3
            )
        )
        dealRepository.save(
            Deal(
                cost = 2000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail3
            )
        )
        dealRepository.save(
            Deal(
                cost = 3000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail4
            )
        )
        dealRepository.save(
            Deal(
                cost = 4000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail4
            )
        )
        dealRepository.save(
            Deal(
                cost = 5000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail4
            )
        )
        dealRepository.save(
            Deal(
                cost = 6000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail5
            )
        )
        dealRepository.save(
            Deal(
                cost = 7000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail5
            )
        )
        dealRepository.save(
            Deal(
                cost = 1000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail5
            )
        )
        dealRepository.save(
            Deal(
                cost = 2000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail6
            )
        )
        dealRepository.save(
            Deal(
                cost = 3000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail6
            )
        )
        dealRepository.save(
            Deal(
                cost = 4000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail6
            )
        )
        dealRepository.save(
            Deal(
                cost = 5000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail7
            )
        )
        dealRepository.save(
            Deal(
                cost = 6000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail7
            )
        )
        dealRepository.save(
            Deal(
                cost = 7000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail7
            )
        )
        dealRepository.save(
            Deal(
                cost = 1000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail8
            )
        )
        dealRepository.save(
            Deal(
                cost = 2000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail8
            )
        )
        dealRepository.save(
            Deal(
                cost = 3000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail8
            )
        )
        dealRepository.save(
            Deal(
                cost = 4000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail9
            )
        )
        dealRepository.save(
            Deal(
                cost = 5000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail9
            )
        )
        dealRepository.save(
            Deal(
                cost = 6000,
                dealDate = LocalDateTime.now(),
                houseDetail = houseDetail9
            )
        )
    }

    Given("deal 필터 요청시") {
        // 테스트를 위한 house, houseDetail, deal 을 repository 에 저장
        saveFilterEntity()
        When("정상 요청하면") {
            val house1 = houseRepository.findByRoadAddressAndDanjiName("roadAddress1", "danjiName1")
            val house2 = houseRepository.findByRoadAddressAndDanjiName("roadAddress2", "danjiName2")
            val houseIdList = listOf(house1.id, house2.id)
            val lowCost = 4000
            val highCost = 5000
            val response = dealRepository.getDealFilterResponseStream(houseIdList, lowCost, highCost)
            Then("houseIdList 이 포함된다") {
                response.forEach {
                    houseIdList shouldContain it.houseId
                    println("houseId: ${it.houseId}, dealId: ${it.dealId}, latitude: ${it.latitude}, longitude: ${it.longitude}")
                }
            }
        }
    }
})