package estert.domain.dealHistory.dto.filter

class DealHistoryFilteredRequest(
    // 분
    val time: Int,
    // 만원단위 가격
    val lowCost: Int,
    val highCost: Int,
    // 위도,경도
    val latitude: String,
    val longitude: String,
    // 반환 개수
    val limit: Int?,
)