package estert.domain.dealHistory.dto.filter

/**
 * @param latitude : 위도
 * @param longitude : 경도
 * @param lowCost : 최저 가격
 * @param highCost : 최고 가격
 * @param time : 이동 시간
 * @param count : 반환할 거래 내역 수
 */
class DealHistoryFilterRequest(
    val latitude: Double,
    val longitude: Double,
    val lowCost: Int,
    val highCost: Int,
    val time: Int,
    val count: Int?
)