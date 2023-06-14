package estert.dummy

import estert.domain.house.House
import estert.domain.house_detail.HouseDetail

object DummyEntity {
    val house = House(
        jibunAddress = "jibunAddress",
        roadAddress = "roadAddress",
        danjiName = "danjiName",
        postCode = "12345",
        latitude = "123.123".toBigDecimal(),
        longitude = "123.123".toBigDecimal()
    )
    val houseDetail = HouseDetail(
        dedicatedArea = "123.123".toBigDecimal(),
        house = house
    )
    val deal = estert.domain.deal.Deal(
        cost = 100000000,
        dealDate = java.time.LocalDateTime.now(),
        houseDetail = houseDetail
    )
}