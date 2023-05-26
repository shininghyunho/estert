package estert.domain.house.dto

import estert.domain.house.House

data class HouseGetResponse(
    val id: Long,
    val jibunAddress: String,
    val roadAddress: String,
    val danjiName: String,
    val postCode: Int,
    val latitude: String,
    val longitude: String,
) {
    companion object {
        fun of(house: House): HouseGetResponse {
            return HouseGetResponse(
                id = house.id,
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