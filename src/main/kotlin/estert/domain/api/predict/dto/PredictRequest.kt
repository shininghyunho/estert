package estert.domain.api.predict.dto

class PredictRequest(
    // 분
    val time: Int,
    // 만원단위 가격
    val price: Int,
    // 위도,경도
    val latitude: String,
    val longitude: String,
)