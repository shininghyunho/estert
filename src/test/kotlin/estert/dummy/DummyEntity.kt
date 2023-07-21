package estert.dummy

import estert.domain.api.molitApart.dto.MolitApart
import estert.domain.api.predict.dto.PredictResponse
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
    val molitApart = MolitApart(
        roadName = "roadName",
        roadNameCityCode = "roadNameCityCode",
        roadNameBuildingMainCode = "roadNameBuildingMainCode",
        roadNameBuildingSubCode = "roadNameBuildingSubCode",
        cost = "100,000,000",
        dedicatedArea = "123.123",
        dealYear = "2021",
        dealMonth = "1",
        dealDay = "1"
    )
    val predictHouseList = listOf(
        PredictResponse.PredictHouse(
            id = 1L,
            time = 30
        ),
        PredictResponse.PredictHouse(
            id = 2L,
            time = 25
        ),
        PredictResponse.PredictHouse(
            id = 3L,
            time = 35
        )
    )
}