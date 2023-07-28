package estert.domain.api.predict.dto

class PredictRequest(
    val time: Int,
    // 위도,경도
    val latitude: Double,
    val longitude: Double
)