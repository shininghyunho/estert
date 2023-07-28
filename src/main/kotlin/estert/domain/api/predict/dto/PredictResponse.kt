package estert.domain.api.predict.dto

import estert.domain.house.dto.HouseGetResponse

class PredictResponse(
    val predictHouseList: List<PredictHouse>
) {
    class PredictHouse(
        val id: Long,
        val time: Int
    )
}