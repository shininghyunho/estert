package estert.domain.dealHistory.dto.filter

class DealHistoryFilteredResponse(
    val roadAddress: String,
    val danjiName: String,
    val cost: Int,
    val time: Int,
    val latitude: String,
    val longitude: String,
    val dealDate: String,
    val dedicatedArea: String
)