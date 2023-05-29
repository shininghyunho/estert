package estert.domain.house_detail.dto

import estert.domain.deal.dto.DealGetResponse
import estert.domain.house_detail.HouseDetail

class HouseDetailGetResponse(
    val id: Long,
    val dedicatedArea: String,
    val deals: MutableSet<DealGetResponse> = hashSetOf(),
) {
    companion object {
        fun fromWithDeals(houseDetail: HouseDetail): HouseDetailGetResponse {
            return HouseDetailGetResponse(
                id = houseDetail.id,
                dedicatedArea = houseDetail.dedicatedArea.toString(),
                deals = houseDetail.deals.map { DealGetResponse.from(it) }.toMutableSet()
            )
        }
    }
}