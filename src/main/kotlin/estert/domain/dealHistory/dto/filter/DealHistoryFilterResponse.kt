package estert.domain.dealHistory.dto.filter

/**
 * @param dealHistoryList : 필터링된 거래 내역 리스트
 */
class DealHistoryFilterResponse(
    val dealHistoryList: List<FilteredDealHistory>
) {
    /**
     * @param dealId : 거래 ID
     * @param latitude : 위도
     * @param longitude : 경도
     * @param estimatedTime : 예상 시간
     */
    class FilteredDealHistory(
        val dealId: Long,
        val latitude: Double,
        val longitude: Double,
        val estimatedTime: Int,
    )
}