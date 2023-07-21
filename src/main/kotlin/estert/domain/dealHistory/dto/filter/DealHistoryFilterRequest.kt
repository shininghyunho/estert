package estert.domain.dealHistory.dto.filter

class DealHistoryFilterRequest(
    val latitude: Double,
    val longitude: Double,
    val lowCost: Int,
    val highCost: Int,
    val time: Int,
    var count: Int
)