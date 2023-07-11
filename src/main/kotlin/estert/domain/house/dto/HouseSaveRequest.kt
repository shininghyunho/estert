package estert.domain.house.dto

import estert.domain.house.House

data class HouseSaveRequest (
    val jibunAddress: String,
    val roadAddress: String,
    val danjiName: String,
    val postCode: String,
    val latitude: String,
    val longitude: String,
)  {
    fun toEntity(): House {
        return House(
            jibunAddress = jibunAddress,
            roadAddress = roadAddress,
            danjiName = danjiName,
            postCode = postCode,
            latitude = latitude.toBigDecimal(),
            longitude = longitude.toBigDecimal(),
        )
    }
    companion object {
        fun from(house: House): HouseSaveRequest {
            return HouseSaveRequest(
                jibunAddress = house.jibunAddress,
                roadAddress = house.roadAddress,
                danjiName = house.danjiName,
                postCode = house.postCode,
                latitude = house.latitude.toString(),
                longitude = house.longitude.toString(),
            )
        }
    }
}