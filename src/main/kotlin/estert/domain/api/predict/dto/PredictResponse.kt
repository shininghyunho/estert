package estert.domain.api.predict.dto

import estert.domain.house.dto.HouseGetResponse

class PredictResponse {
    val houses = mutableListOf<HouseGetResponse>()
}