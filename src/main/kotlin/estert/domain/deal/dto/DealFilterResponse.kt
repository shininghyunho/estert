package estert.domain.deal.dto

import java.math.BigDecimal

class DealFilterResponse(
    val houseId: Long,
    val dealId: Long,
    val latitude: Double,
    val longitude: Double,
) {
    constructor(houseId: Long, dealId: Long, latitude: BigDecimal, longitude: BigDecimal) : this(
        houseId = houseId,
        dealId = dealId,
        latitude = latitude.toDouble(),
        longitude = longitude.toDouble()
    )
}