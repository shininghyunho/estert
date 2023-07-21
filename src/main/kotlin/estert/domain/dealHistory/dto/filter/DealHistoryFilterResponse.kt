package estert.domain.dealHistory.dto.filter

class DealHistoryFilterResponse(
    val dealHistoryList: List<FilteredDealHistory>
) {
    class FilteredDealHistory(
        val dealId: Long,
        val latitude: Double,
        val longitude: Double,
        val estimatedTime: Int,
    )
}