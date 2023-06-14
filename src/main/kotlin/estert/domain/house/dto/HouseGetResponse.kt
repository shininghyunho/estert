package estert.domain.house.dto

import estert.domain.house.House
import estert.domain.house_detail.dto.HouseDetailGetResponse

data class HouseGetResponse(
    val id: Long,
    val jibunAddress: String,
    val roadAddress: String,
    val danjiName: String,
    val postCode: String,
    val latitude: String,
    val longitude: String,
    val houseDetails: MutableSet<HouseDetailGetResponse> = hashSetOf(),
) {
    companion object {
        fun from(house: House): HouseGetResponse {
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

        // with houseDetails
        fun fromWithHouseDetails(house: House): HouseGetResponse {
            return HouseGetResponse(
                id = house.id,
                jibunAddress = house.jibunAddress,
                roadAddress = house.roadAddress,
                danjiName = house.danjiName,
                postCode = house.postCode,
                latitude = house.latitude.toString(),
                longitude = house.longitude.toString(),
                houseDetails = house.houseDetails.map { HouseDetailGetResponse.fromWithDeals(it) }.toMutableSet()
            )
        }
    }
}