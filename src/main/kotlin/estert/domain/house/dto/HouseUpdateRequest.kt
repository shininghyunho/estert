package estert.domain.house.dto

import estert.domain.house.House

class HouseUpdateRequest (
    val jibunAddress: String?=null,
    val roadAddress: String?=null,
    val danjiName: String?=null,
    val postCode: String?=null,
    val latitude: String?=null,
    val longitude: String?=null,
)