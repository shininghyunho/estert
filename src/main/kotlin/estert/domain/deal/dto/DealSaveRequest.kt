package estert.domain.deal.dto

import estert.domain.deal.Deal

class DealSaveRequest(
    val cost: Long,
    val dealDate: String,
    val houseDetailId: Long
)