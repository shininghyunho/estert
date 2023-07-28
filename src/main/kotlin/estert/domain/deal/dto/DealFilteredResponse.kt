package estert.domain.deal.dto

import java.time.LocalDateTime

class DealFilteredResponse (
    val houseId: Long,
    val roadAddress: String,
    val danjiName: String,
    val cost: Long,
    val latitude: Double,
    val longitude: Double,
    val dealDate: LocalDateTime,
    val dedicatedArea: Double
)